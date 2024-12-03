package itmo.is.project.service.trade;

import itmo.is.project.dto.trade.TradeDto;
import itmo.is.project.dto.trade.TradeOfferDto;
import itmo.is.project.dto.trade.TradeRequest;
import itmo.is.project.mapper.trade.TradeMapper;
import itmo.is.project.model.resource.ResourceIdAmountHolder;
import itmo.is.project.model.trade.Operation;
import itmo.is.project.model.trade.Trade;
import itmo.is.project.model.trade.TradeItem;
import itmo.is.project.model.user.User;
import itmo.is.project.repository.trade.TradeItemRepository;
import itmo.is.project.repository.trade.TradeRepository;
import itmo.is.project.service.module.StorageModuleService;
import itmo.is.project.service.resource.ResourceService;
import itmo.is.project.service.user.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final TradeItemRepository tradeItemRepository;
    private final TradeRepository tradeRepository;
    private final TradeMapper tradeMapper;

    private final AccountService accountService;
    private final StorageModuleService storageModuleService;
    private final ResourceService resourceService;

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

        List<TradeItem> sell = createTradeItems(request.buyFromStation(), Operation.SELL, trade);
        List<TradeItem> purchase = createTradeItems(request.sellToStation(), Operation.BUY, trade);

        int stationBalanceChange = calculateStationBalanceChange(sell, purchase);
        accountService.transferFundsBetweenStationAndUser(user.getId(), stationBalanceChange);
        storageModuleService.retrieveAndStoreAll(sell, purchase);

        trade = tradeRepository.save(trade);
        tradeItemRepository.saveAll(sell);
        tradeItemRepository.saveAll(purchase);

        return tradeMapper.toDto(trade);
    }

    private List<TradeItem> createTradeItems(
            Collection<? extends ResourceIdAmountHolder> resources,
            Operation operation, Trade trade
    ) {
        return resources.stream()
                .map(resourceService::toResourceAmount)
                .map(resourceAmount -> {
                    TradeOfferDto offer = getTradeOffer(resourceAmount.getResourceId(), operation);
                    if (offer.amount() < resourceAmount.getAmount()) {
                        throw new IllegalArgumentException();
                    }
                    return new TradeItem(trade, resourceAmount, operation, offer.price());
                })
                .toList();
    }

    private TradeOfferDto getTradeOffer(Integer resourceId, Operation operation) {
        return switch (operation) {
            case BUY -> tradeRepository.findPurchaseOfferByResourceId(resourceId);
            case SELL -> tradeRepository.findSellOfferByResourceId(resourceId);
        };
    }

    private int calculateStationBalanceChange(List<TradeItem> sell, List<TradeItem> purchase) {
        int income = calculateSumPrice(sell);
        int outcome = calculateSumPrice(purchase);
        return income - outcome;
    }

    private int calculateSumPrice(List<TradeItem> items) {
        return items.stream()
                .mapToInt(TradeItem::getPrice)
                .sum();
    }
}
