package itmo.is.project.rest.controller.module;

import itmo.is.project.dto.ProductionModuleDto;
import itmo.is.project.service.module.production.ProductionModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/production")
@RequiredArgsConstructor
public class ProductionModuleRestController {
    private final ProductionModuleService productionModuleService;

    @GetMapping("/modules")
    public ResponseEntity<Page<ProductionModuleDto>> getModules(Pageable pageable) {
        return ResponseEntity.ok(productionModuleService.findAllModules(pageable));
    }
}
