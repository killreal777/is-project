package itmo.is.project.controller.module;

import itmo.is.project.dto.module.BuildModuleRequest;
import itmo.is.project.dto.module.production.ProductionModuleBlueprintDto;
import itmo.is.project.dto.module.production.ProductionModuleDto;
import itmo.is.project.service.module.ProductionModuleService;
import itmo.is.project.service.module.build.ProductionModuleBuildService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/modules/production")
@RequiredArgsConstructor
public class ProductionModuleRestController {
    private final ProductionModuleService productionModuleService;
    private final ProductionModuleBuildService productionModuleBuildService;

    @GetMapping
    public ResponseEntity<Page<ProductionModuleDto>> getAllProductionModules(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(productionModuleService.getAllProductionModules(pageable));
    }

    @GetMapping("/{productionModuleId}")
    public ResponseEntity<ProductionModuleDto> getProductionModule(@PathVariable Integer productionModuleId) {
        return ResponseEntity.ok(productionModuleService.getProductionModuleById(productionModuleId));
    }

    @GetMapping("/build/blueprints")
    public ResponseEntity<Page<ProductionModuleBlueprintDto>> findAllBlueprints(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(productionModuleBuildService.findAllBlueprints(pageable));
    }

    @PostMapping("/build")
    public ResponseEntity<ProductionModuleDto> buildModule(@RequestBody BuildModuleRequest buildModuleRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productionModuleBuildService.buildModule(buildModuleRequest));
    }

    @PostMapping("/{productionModuleId}/engineer")
    public ResponseEntity<ProductionModuleDto> assignEngineer(
            @PathVariable Integer productionModuleId,
            @RequestBody Integer engineerId
    ) {
        return ResponseEntity.ok(productionModuleService.assignEngineer(productionModuleId, engineerId));
    }

    @DeleteMapping("/{productionModuleId}/engineer")
    public ResponseEntity<ProductionModuleDto> removeEngineer(@PathVariable Integer productionModuleId) {
        return ResponseEntity.ok(productionModuleService.removeEngineer(productionModuleId));
    }

    @PostMapping("/{productionModuleId}/start")
    public ResponseEntity<ProductionModuleDto> start(@PathVariable Integer productionModuleId) {
        return ResponseEntity.ok(productionModuleService.start(productionModuleId));
    }

    @PostMapping("/{productionModuleId}/stop")
    public ResponseEntity<ProductionModuleDto> stop(@PathVariable Integer productionModuleId) {
        return ResponseEntity.ok(productionModuleService.stop(productionModuleId));
    }

    @PostMapping("/{productionModuleId}/load")
    public ResponseEntity<ProductionModuleDto> loadConsumingResources(@PathVariable Integer productionModuleId) {
        return ResponseEntity.ok(productionModuleService.loadConsumingResources(productionModuleId));
    }

    @PostMapping("/{productionModuleId}/store")
    public ResponseEntity<ProductionModuleDto> storeProducedResources(@PathVariable Integer productionModuleId) {
        return ResponseEntity.ok(productionModuleService.storeProducedResources(productionModuleId));
    }
}
