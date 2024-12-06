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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trades")
@RequiredArgsConstructor
public class TradeRestController {
    private final TradeService tradeService;

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping
    public ResponseEntity<Page<TradeDto>> getAllTrades(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(tradeService.getAllTrades(pageable));
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping("/{tradeId}")
    public ResponseEntity<TradeDto> getTradeById(@PathVariable Integer tradeId) {
        return ResponseEntity.ok(tradeService.getTradeById(tradeId));
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping("/users/{userId}")
    public ResponseEntity<Page<TradeDto>> getTradesByUserId(
            @PathVariable Integer userId,
            @PageableDefault Pageable pageable
    ) {
        return ResponseEntity.ok(tradeService.getAllTradesByUserId(userId, pageable));
    }

    @GetMapping("/users/me")
    public ResponseEntity<Page<TradeDto>> getMyTrades(
            @AuthenticationPrincipal User user,
            @PageableDefault Pageable pageable
    ) {
        return ResponseEntity.ok(tradeService.getAllTradesByUserId(user.getId(), pageable));
    }

    @GetMapping("/offers/sell")
    public ResponseEntity<Page<TradeOfferDto>> getAllSellOffers(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(tradeService.getAllSellOffers(pageable));
    }

    @GetMapping("/offers/sell/{resourceId}")
    public ResponseEntity<TradeOfferDto> getSellOfferByResourceId(@PathVariable("resourceId") Integer resourceId) {
        return ResponseEntity.ok(tradeService.getSellOfferByResourceId(resourceId));
    }

    @GetMapping("/offers/purchase")
    public ResponseEntity<Page<TradeOfferDto>> getAllPurchaseOffers(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(tradeService.getAllPurchaseOffers(pageable));
    }

    @GetMapping("/offers/purchase/{resourceId}")
    public ResponseEntity<TradeOfferDto> getPurchaseOfferByResourceId(@PathVariable("resourceId") Integer resourceId) {
        return ResponseEntity.ok(tradeService.getPurchaseOfferByResourceId(resourceId));
    }

    @PostMapping
    public ResponseEntity<TradeDto> trade(@RequestBody TradeRequest request, @AuthenticationPrincipal User user) {
        TradeDto trade = tradeService.trade(request, user);
        return ResponseEntity.ok(trade);
    }
}
