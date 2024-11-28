package itmo.is.project.service.trade;

import itmo.is.project.dto.trade.TradeDto;
import itmo.is.project.dto.trade.TradeOfferDto;
import itmo.is.project.dto.trade.TradeRequest;
import itmo.is.project.mapper.trade.TradeMapper;
import itmo.is.project.model.user.User;
import itmo.is.project.repository.trade.TradeRepository;
import itmo.is.project.service.module.StorageService;
import itmo.is.project.service.user.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradeService {
    private final TradeRepository tradeRepository;
    private final TradeMapper tradeMapper;
    private final AccountService accountService;
    private final StorageService storageService;

//    private final

    public Page<TradeDto> getAllTrades(Pageable pageable) {
        return tradeRepository.findAll(pageable).map(tradeMapper::toDto);
    }


    public Page<TradeOfferDto> getAllTradeOffersStationBuys(Pageable pageable) {
        return tradeRepository.findAllPurchaseOffers(pageable);
    }

    public Page<TradeOfferDto> getAllTradeOffersStationSells(Pageable pageable) {
        return tradeRepository.findAllSellOffers(pageable);
    }

    public TradeDto trade(TradeRequest request, User user) {
        return null;
    }
}
