package itmo.is.project.rest.controller.module;

import itmo.is.project.dto.module.production.ProductionModuleBlueprintDto;
import itmo.is.project.dto.module.production.ProductionModuleDto;
import itmo.is.project.service.module.ProductionModuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/modules/production")
public class ProductionModuleRestController
        extends ModuleRestController<ProductionModuleDto, ProductionModuleBlueprintDto> {

    private final ProductionModuleService productionModuleService;

    public ProductionModuleRestController(ProductionModuleService productionModuleService) {
        super(productionModuleService);
        this.productionModuleService = productionModuleService;
    }

    @PostMapping("/{productionModuleId}/engineer")
    public ResponseEntity<ProductionModuleDto> assignEngineer(
            @PathVariable Integer productionModuleId,
            @RequestBody Integer engineerId
    ) {
        return ResponseEntity.ok(productionModuleService.assignEngineer(productionModuleId, engineerId));
    }

    @DeleteMapping("/{productionModuleId}/engineer")
    public ResponseEntity<ProductionModuleDto> removeEngineer(
            @PathVariable Integer productionModuleId
    ) {
        return ResponseEntity.ok(productionModuleService.removeEngineer(productionModuleId));
    }

    @PostMapping("/{productionModuleId}/start")
    public ResponseEntity<ProductionModuleDto> start(
            @PathVariable Integer productionModuleId
    ) {
        return ResponseEntity.ok(productionModuleService.start(productionModuleId));
    }

    @PostMapping("/{productionModuleId}/stop")
    public ResponseEntity<ProductionModuleDto> stop(
            @PathVariable Integer productionModuleId
    ) {
        return ResponseEntity.ok(productionModuleService.stop(productionModuleId));
    }

    @PostMapping("/{productionModuleId}/load")
    public ResponseEntity<ProductionModuleDto> loadConsumingResources(
            @PathVariable Integer productionModuleId
    ) {
        return ResponseEntity.ok(productionModuleService.loadConsumingResources(productionModuleId));
    }

    @PostMapping("/{productionModuleId}/store")
    public ResponseEntity<ProductionModuleDto> storeProducedResources(
            @PathVariable Integer productionModuleId
    ) {
        return ResponseEntity.ok(productionModuleService.storeProducedResources(productionModuleId));
    }
}
