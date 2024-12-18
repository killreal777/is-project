package itmo.is.project.service.trade;

import itmo.is.project.dto.resource.ResourceIdAmountDto;
import itmo.is.project.dto.trade.TradeDto;
import itmo.is.project.dto.trade.TradeOfferDto;
import itmo.is.project.dto.trade.TradeRequest;
import itmo.is.project.mapper.trade.TradeMapper;
import itmo.is.project.model.resource.ResourceIdAmount;
import itmo.is.project.model.trade.Operation;
import itmo.is.project.model.trade.Trade;
import itmo.is.project.model.trade.TradeItem;
import itmo.is.project.model.user.User;
import itmo.is.project.repository.trade.TradeItemRepository;
import itmo.is.project.repository.trade.TradeRepository;
import itmo.is.project.service.module.StorageModuleService;
import itmo.is.project.service.resource.ResourceService;
import itmo.is.project.service.user.AccountService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public TradeDto getTradeById(Integer id) {
        return tradeRepository.findById(id).map(tradeMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Trade not found with id: " + id));
    }

    public Page<TradeDto> getAllTradesByUserId(Integer userId, Pageable pageable) {
        return tradeRepository.findAllByUserId(userId, pageable).map(tradeMapper::toDto);
    }

    public Page<TradeOfferDto> getAllPurchaseOffers(Pageable pageable) {
        return tradeRepository.findAllPurchaseOffers(pageable);
    }

    public Page<TradeOfferDto> getAllSellOffers(Pageable pageable) {
        return tradeRepository.findAllSellOffers(pageable);
    }

    public TradeOfferDto getSellOfferByResourceId(Integer resourceId) {
        return tradeRepository.findSellOfferByResourceId(resourceId).orElseThrow(() ->
                new EntityNotFoundException("Sell offer not found with resource id: " + resourceId)
        );
    }

    public TradeOfferDto getPurchaseOfferByResourceId(Integer resourceId) {
        return tradeRepository.findPurchaseOfferByResourceId(resourceId).orElseThrow(() ->
                new EntityNotFoundException("Purchase offer not found with resource id: " + resourceId)
        );
    }

    @Transactional
    public TradeDto trade(TradeRequest request, User user) {
        Trade trade = new Trade();
        trade.setUser(user);

        List<TradeItem> sell = createTradeItems(request.buy(), Operation.SELL, trade);
        List<TradeItem> purchase = createTradeItems(request.sell(), Operation.BUY, trade);

        int stationBalanceChange = calculateStationBalanceChange(sell, purchase);
        accountService.transferFundsBetweenStationAndUser(user.getId(), stationBalanceChange);
        storageModuleService.retrieveAndStoreAll(sell, purchase);

        trade.setItems(new ArrayList<>(sell));
        trade.getItems().addAll(purchase);
        trade = tradeRepository.save(trade);

        return tradeMapper.toDto(trade);
    }

    private List<TradeItem> createTradeItems(
            Collection<ResourceIdAmountDto> resources,
            Operation operation, Trade trade
    ) {
        return resources.stream()
                .map((dto -> new ResourceIdAmount(dto.id(), dto.amount())))
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
            case BUY -> getSellOfferByResourceId(resourceId);      // user buys -> station sells
            case SELL -> getPurchaseOfferByResourceId(resourceId); // user sells -> station buys
        };
    }

    private int calculateStationBalanceChange(List<TradeItem> sell, List<TradeItem> purchase) {
        int income = calculateSumPrice(sell);
        int outcome = calculateSumPrice(purchase);
        return income - outcome;
    }

    private int calculateSumPrice(List<TradeItem> items) {
        return items.stream()
                .mapToInt(item -> item.getPrice() * item.getAmount())
                .sum();
    }
}
