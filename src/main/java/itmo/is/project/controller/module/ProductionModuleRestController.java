package itmo.is.project.controller.module;

import itmo.is.project.dto.module.BuildModuleRequest;
import itmo.is.project.dto.module.production.ProductionModuleBlueprintDto;
import itmo.is.project.dto.module.production.ProductionModuleDto;
import itmo.is.project.service.module.ProductionService;
import itmo.is.project.service.module.build.ProductionModuleBuildService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/modules/production")
@RequiredArgsConstructor
public class ProductionModuleRestController {

    private final ProductionService productionService;
    private final ProductionModuleBuildService productionModuleBuildService;

    @GetMapping("/build/blueprints")
    public ResponseEntity<Page<ProductionModuleBlueprintDto>> findAllBlueprints(Pageable pageable) {
        return ResponseEntity.ok(productionModuleBuildService.findAllBlueprints(pageable));
    }

    @PostMapping("/build")
    public ResponseEntity<ProductionModuleDto> buildModule(
            @RequestBody BuildModuleRequest buildModuleRequest
    ) {
        return ResponseEntity.ok(productionModuleBuildService.buildModule(buildModuleRequest));
    }

    @PostMapping("/{productionModuleId}/engineer")
    public ResponseEntity<ProductionModuleDto> assignEngineer(
            @PathVariable Integer productionModuleId,
            @RequestBody Integer engineerId
    ) {
        return ResponseEntity.ok(productionService.assignEngineer(productionModuleId, engineerId));
    }

    @DeleteMapping("/{productionModuleId}/engineer")
    public ResponseEntity<ProductionModuleDto> removeEngineer(
            @PathVariable Integer productionModuleId
    ) {
        return ResponseEntity.ok(productionService.removeEngineer(productionModuleId));
    }

    @PostMapping("/{productionModuleId}/start")
    public ResponseEntity<ProductionModuleDto> start(
            @PathVariable Integer productionModuleId
    ) {
        return ResponseEntity.ok(productionService.start(productionModuleId));
    }

    @PostMapping("/{productionModuleId}/stop")
    public ResponseEntity<ProductionModuleDto> stop(
            @PathVariable Integer productionModuleId
    ) {
        return ResponseEntity.ok(productionService.stop(productionModuleId));
    }

    @PostMapping("/{productionModuleId}/load")
    public ResponseEntity<ProductionModuleDto> loadConsumingResources(
            @PathVariable Integer productionModuleId
    ) {
        return ResponseEntity.ok(productionService.loadConsumingResources(productionModuleId));
    }

    @PostMapping("/{productionModuleId}/store")
    public ResponseEntity<ProductionModuleDto> storeProducedResources(
            @PathVariable Integer productionModuleId
    ) {
        return ResponseEntity.ok(productionService.storeProducedResources(productionModuleId));
    }
}
