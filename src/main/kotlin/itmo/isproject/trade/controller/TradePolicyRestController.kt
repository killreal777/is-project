package itmo.isproject.trade.controller

import itmo.isproject.trade.dto.policy.TradePolicyDto
import itmo.isproject.trade.dto.policy.UpdateTradePolicyRequest
import itmo.isproject.trade.service.TradePolicyService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/trades/policies")
class TradePolicyRestController(
    private val service: TradePolicyService
) {

    @GetMapping
    fun getAll(@PageableDefault pageable: Pageable): ResponseEntity<Page<TradePolicyDto>> {
        return ResponseEntity.ok(service.getAll(pageable))
    }

    @GetMapping("/purchase")
    fun getAllStationBuys(@PageableDefault pageable: Pageable): ResponseEntity<Page<TradePolicyDto>> {
        return ResponseEntity.ok(service.getAllStationBuys(pageable))
    }

    @GetMapping("/sell")
    fun getAllStationSells(@PageableDefault pageable: Pageable): ResponseEntity<Page<TradePolicyDto>> {
        return ResponseEntity.ok(service.getAllStationSells(pageable))
    }

    @GetMapping("/{resourceId}")
    fun getByResourceId(@PathVariable resourceId: Int): ResponseEntity<TradePolicyDto> {
        return ResponseEntity.ok(service.getByResourceId(resourceId))
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @PutMapping("/{resourceId}")
    fun updateByResourceId(
        @PathVariable resourceId: Int,
        @RequestBody request: UpdateTradePolicyRequest
    ): ResponseEntity<TradePolicyDto> {
        return ResponseEntity.ok(service.updateByResourceId(resourceId, request))
    }
}
