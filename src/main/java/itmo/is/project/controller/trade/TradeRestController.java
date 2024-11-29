package itmo.is.project.controller.trade;

import itmo.is.project.dto.trade.TradeDto;
import itmo.is.project.dto.trade.TradeOfferDto;
import itmo.is.project.dto.trade.TradeRequest;
import itmo.is.project.model.user.User;
import itmo.is.project.service.trade.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/management/trades")
@RequiredArgsConstructor
public class TradeRestController {
    private final TradeService tradeService;

    @GetMapping
    public ResponseEntity<Page<TradeDto>> getAllTrades(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(tradeService.getAllTrades(pageable));
    }
    
    @GetMapping("/offers/stationBuys")
    public ResponseEntity<Page<TradeOfferDto>> getAllTradeOffersStationBuys(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(tradeService.getAllTradeOffersStationBuys(pageable));
    }

    @GetMapping("/offers/stationSells")
    public ResponseEntity<Page<TradeOfferDto>> getAllTradeOffersStationSells(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(tradeService.getAllTradeOffersStationSells(pageable));
    }

    @PostMapping
    public ResponseEntity<TradeDto> trade(@RequestBody TradeRequest request, @AuthenticationPrincipal User user) {
        TradeDto trade = tradeService.trade(request, user);
        return ResponseEntity.ok(trade);
    }
}
