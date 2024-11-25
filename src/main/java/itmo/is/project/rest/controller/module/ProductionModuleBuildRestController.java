package itmo.is.project.rest.controller;

import itmo.is.project.dto.ProductionModuleBlueprintDto;
import itmo.is.project.dto.ProductionModuleDto;
import itmo.is.project.dto.request.ModuleBuildRequest;
import itmo.is.project.service.module.production.ProductionModuleBuildService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/modules/production/build")
@RequiredArgsConstructor
public class ProductionModuleBuildRestController {
    private final ProductionModuleBuildService productionModuleBuildService;

    @GetMapping("/blueprints")
    public ResponseEntity<Page<ProductionModuleBlueprintDto>> findAllBlueprints(Pageable pageable) {
        return ResponseEntity.ok(productionModuleBuildService.findAllBlueprints(pageable));
    }

    @PostMapping
    public ResponseEntity<ProductionModuleDto> buildModule(
            @RequestBody ModuleBuildRequest moduleBuildRequest
    ) {
        return ResponseEntity.ok(productionModuleBuildService.buildModule(moduleBuildRequest));
    }
}
