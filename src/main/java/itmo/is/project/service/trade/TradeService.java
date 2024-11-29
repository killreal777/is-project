package itmo.is.project.service.trade;

import itmo.is.project.dto.trade.TradeDto;
import itmo.is.project.dto.trade.TradeOfferDto;
import itmo.is.project.dto.trade.TradeRequest;
import itmo.is.project.dto.user.AccountDto;
import itmo.is.project.mapper.trade.TradeMapper;
import itmo.is.project.model.resource.Resource;
import itmo.is.project.model.resource.ResourceAmountHolder;
import itmo.is.project.model.resource.ResourceIdAmount;
import itmo.is.project.model.trade.Operation;
import itmo.is.project.model.trade.Trade;
import itmo.is.project.model.trade.TradeItem;
import itmo.is.project.model.user.User;
import itmo.is.project.repository.ResourceRepository;
import itmo.is.project.repository.trade.TradeItemRepository;
import itmo.is.project.repository.trade.TradeRepository;
import itmo.is.project.service.module.StorageService;
import itmo.is.project.service.user.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradeService {
    private final TradeItemRepository tradeItemRepository;
    private final TradeRepository tradeRepository;
    private final TradeMapper tradeMapper;
    private final AccountService accountService;
    private final StorageService storageService;
    private final ResourceRepository resourceRepository;

    public Page<TradeDto> getAllTrades(Pageable pageable) {
        return tradeRepository.findAll(pageable).map(tradeMapper::toDto);
    }

    public Page<TradeOfferDto> getAllTradeOffersStationBuys(Pageable pageable) {
        return tradeRepository.findAllPurchaseOffers(pageable);
    }

    public Page<TradeOfferDto> getAllTradeOffersStationSells(Pageable pageable) {
        return tradeRepository.findAllSellOffers(pageable);
    }

    @Transactional
    public TradeDto trade(TradeRequest request, User user) {
        Trade trade = new Trade();
        trade.setUser(user);
        Map<TradeOfferDto, TradeItem> sell = mapToOffersAndItems(request.buyFromStation(), Operation.SELL, trade);
        Map<TradeOfferDto, TradeItem> purchase = mapToOffersAndItems(request.sellToStation(), Operation.BUY, trade);
        int stationBalanceChange = calculateStationBalanceChange(sell, purchase);
        AccountDto userAccount = accountService.findAccountByUserId(user.getId());
        AccountDto stationAccount = accountService.findStationAccount();
        validateRequest(sell, purchase, userAccount, stationAccount, stationBalanceChange);
        transferResources(sell.values(), purchase.values());
        transferFunds(userAccount, stationAccount, stationBalanceChange);
        return tradeMapper.toDto(saveTrade(trade, sell.values(), purchase.values()));
    }

    private Map<TradeOfferDto, TradeItem> mapToOffersAndItems(
            Set<ResourceIdAmount> resources,
            Operation operation,
            Trade trade
    ) {
        return resources.stream()
                .map(resource -> Pair.of(getTradeOfferByResourceIdAndOperation(resource.getId(), operation), resource))
                .collect(Collectors.toMap(
                        Pair::getFirst,
                        pair -> createTradeItem(pair.getFirst(), pair.getSecond(), operation, trade)
                ));
    }

    private TradeOfferDto getTradeOfferByResourceIdAndOperation(Integer resourceId, Operation operation) {
        return switch (operation) {
            case BUY -> tradeRepository.findPurchaseOfferByResourceId(resourceId);
            case SELL -> tradeRepository.findSellOfferByResourceId(resourceId);
        };
    }

    private TradeItem createTradeItem(
            TradeOfferDto offer,
            ResourceIdAmount resourceIdAmount,
            Operation operation,
            Trade trade
    ) {
        Resource resource = resourceRepository.findById(resourceIdAmount.getId()).orElseThrow();
        TradeItem tradeItem = new TradeItem();
        tradeItem.setCompositeKey(trade, resource);
        tradeItem.setAmount(resourceIdAmount.getAmount());
        tradeItem.setOperation(operation);
        tradeItem.setPrice(offer.price());
        return tradeItem;
    }

    private int calculateStationBalanceChange(
            Map<TradeOfferDto, TradeItem> sellOffers,
            Map<TradeOfferDto, TradeItem> purchaseOffers
    ) {
        int income = calculateSumPrice(sellOffers);
        int outcome = calculateSumPrice(purchaseOffers);
        return income - outcome;
    }

    private int calculateSumPrice(Map<TradeOfferDto, TradeItem> offers) {
        return offers.entrySet().stream()
                .map(entry -> entry.getKey().price() * entry.getValue().getAmount())
                .reduce(0, Integer::sum);
    }

    private void validateRequest(
            Map<TradeOfferDto, TradeItem> sell,
            Map<TradeOfferDto, TradeItem> purchase,
            AccountDto userAccount,
            AccountDto stationAccount,
            int stationBalanceChange
    ) {
        checkLimits(sell);
        checkLimits(purchase);
        checkFunds(userAccount, stationAccount, stationBalanceChange);
        checkFreeSpace(sell.values(), purchase.values());
    }

    private void checkLimits(Map<TradeOfferDto, TradeItem> offers) {
        for (Map.Entry<TradeOfferDto, TradeItem> entry : offers.entrySet()) {
            if (entry.getValue().getAmount() > entry.getKey().amount()) {
                System.out.println("LIMITS");
                throw new IllegalArgumentException();
            }
        }
    }

    private void checkFunds(AccountDto userAccount, AccountDto stationAccount, int stationBalanceChange) {
        if (stationBalanceChange > 0 && userAccount.balance() < stationBalanceChange) {
            System.out.println("USER BALANCE");
            throw new IllegalStateException();
        } else if (stationBalanceChange < 0 && stationAccount.balance() < -stationBalanceChange) {
            System.out.println("STATION BALANCE");
            throw new IllegalStateException();
        }
    }

    private void checkFreeSpace(
            Collection<? extends ResourceAmountHolder> retrieve,
            Collection<? extends ResourceAmountHolder> store) {
        int retrieveAmount = calculateTotalAmount(retrieve);
        int storeAmount = calculateTotalAmount(store);
        int requiredFreeSpace = storeAmount - retrieveAmount;
        if (requiredFreeSpace > 0 && requiredFreeSpace > storageService.getTotalFreeSpace()) {
            System.out.println("FREE SPACE");
            throw new IllegalStateException();
        }
    }

    private int calculateTotalAmount(Collection<? extends ResourceAmountHolder> resources) {
        return resources.stream()
                .map(ResourceAmountHolder::getAmount)
                .reduce(0, Integer::sum);
    }

    private void transferResources(Collection<TradeItem> retrieve, Collection<TradeItem> store) {
        storageService.retrieveAndStoreAll(
                retrieve.stream().map(TradeItem::getResourceIdAmount).toList(),
                store.stream().map(TradeItem::getResourceIdAmount).toList()
        );
    }

    private void transferFunds(AccountDto userAccount, AccountDto stationAccount, int stationBalanceChange) {
        if (stationBalanceChange > 0) {
            accountService.transferFunds(userAccount, stationAccount, stationBalanceChange);
        } else if (stationBalanceChange < 0) {
            accountService.transferFunds(stationAccount, userAccount, -stationBalanceChange);
        }
    }

    private Trade saveTrade(Trade trade, Collection<TradeItem> sell, Collection<TradeItem> purchase) {
        trade = tradeRepository.save(trade);
        tradeItemRepository.saveAll(sell);
        tradeItemRepository.saveAll(purchase);
        return trade;
    }
}
