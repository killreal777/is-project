package itmo.isproject.controller.trade

import itmo.isproject.dto.trade.TradeDto
import itmo.isproject.dto.trade.TradeOfferDto
import itmo.isproject.dto.trade.TradeRequest
import itmo.isproject.model.user.User
import itmo.isproject.service.trade.TradeService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/trades")
class TradeRestController(
    private val tradeService: TradeService
) {

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping
    fun getAllTrades(@PageableDefault pageable: Pageable): ResponseEntity<Page<TradeDto>> {
        return ResponseEntity.ok(tradeService.getAllTrades(pageable))
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping("/{tradeId}")
    fun getTradeById(@PathVariable tradeId: Int): ResponseEntity<TradeDto> {
        return ResponseEntity.ok(tradeService.getTradeById(tradeId))
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping("/users/{userId}")
    fun getTradesByUserId(
        @PathVariable userId: Int,
        @PageableDefault pageable: Pageable
    ): ResponseEntity<Page<TradeDto>> {
        return ResponseEntity.ok(tradeService.getAllTradesByUserId(userId, pageable))
    }

    @GetMapping("/users/me")
    fun getMyTrades(
        @AuthenticationPrincipal user: User,
        @PageableDefault pageable: Pageable
    ): ResponseEntity<Page<TradeDto>> {
        return ResponseEntity.ok(tradeService.getAllTradesByUserId(user.id!!, pageable))
    }

    @GetMapping("/offers/sell")
    fun getAllSellOffers(@PageableDefault pageable: Pageable): ResponseEntity<Page<TradeOfferDto>> {
        return ResponseEntity.ok(tradeService.getAllSellOffers(pageable))
    }

    @GetMapping("/offers/sell/{resourceId}")
    fun getSellOfferByResourceId(@PathVariable("resourceId") resourceId: Int): ResponseEntity<TradeOfferDto> {
        return ResponseEntity.ok(tradeService.getSellOfferByResourceId(resourceId))
    }

    @GetMapping("/offers/purchase")
    fun getAllPurchaseOffers(@PageableDefault pageable: Pageable): ResponseEntity<Page<TradeOfferDto>> {
        return ResponseEntity.ok(tradeService.getAllPurchaseOffers(pageable))
    }

    @GetMapping("/offers/purchase/{resourceId}")
    fun getPurchaseOfferByResourceId(@PathVariable("resourceId") resourceId: Int): ResponseEntity<TradeOfferDto> {
        return ResponseEntity.ok(tradeService.getPurchaseOfferByResourceId(resourceId))
    }

    @PostMapping
    fun trade(@RequestBody request: TradeRequest, @AuthenticationPrincipal user: User): ResponseEntity<TradeDto> {
        val trade = tradeService.trade(request, user)
        return ResponseEntity.ok(trade)
    }
}
