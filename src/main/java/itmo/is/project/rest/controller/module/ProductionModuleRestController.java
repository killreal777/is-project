package itmo.is.project.rest.controller.module;

import itmo.is.project.dto.module.production.ProductionModuleBlueprintDto;
import itmo.is.project.dto.module.production.ProductionModuleDto;
import itmo.is.project.service.module.ProductionModuleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/modules/production")
public class ProductionModuleRestController
        extends ModuleRestController<ProductionModuleDto, ProductionModuleBlueprintDto> {

    public ProductionModuleRestController(ProductionModuleService productionModuleService) {
        super(productionModuleService);
    }
}
