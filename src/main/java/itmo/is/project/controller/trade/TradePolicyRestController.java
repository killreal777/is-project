package itmo.is.project.controller.trade;

import itmo.is.project.dto.trade.policy.TradePolicyDto;
import itmo.is.project.dto.trade.policy.UpdateTradePolicyRequest;
import itmo.is.project.service.trade.TradePolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/management/trades/policies")
@RequiredArgsConstructor
public class TradePolicyRestController {
    private final TradePolicyService service;

    @GetMapping
    public ResponseEntity<Page<TradePolicyDto>> getAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @GetMapping("/stationBuys")
    public ResponseEntity<Page<TradePolicyDto>> getAllStationBuys(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(service.getAllStationBuys(pageable));
    }

    @GetMapping("/stationSells")
    public ResponseEntity<Page<TradePolicyDto>> getAllStationSells(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(service.getAllStationSells(pageable));
    }

    @GetMapping("/{resourceId}")
    public ResponseEntity<TradePolicyDto> getByResourceId(@PathVariable Integer resourceId) {
        return ResponseEntity.ok(service.getByResourceId(resourceId));
    }

    @PutMapping("/{resourceId}")
    public ResponseEntity<TradePolicyDto> updateByResourceId(
            @PathVariable Integer resourceId,
            @RequestBody UpdateTradePolicyRequest request
    ) {
        return ResponseEntity.ok(service.updateByResourceId(resourceId, request));
    }
}
