package itmo.is.project.service.module;

import itmo.is.project.dto.module.production.ProductionModuleBlueprintDto;
import itmo.is.project.dto.module.production.ProductionModuleDto;
import itmo.is.project.mapper.module.ProductionModuleBlueprintMapper;
import itmo.is.project.mapper.module.ProductionModuleMapper;
import itmo.is.project.model.module.production.ProductionModule;
import itmo.is.project.model.module.production.ProductionModuleBlueprint;
import itmo.is.project.repository.module.production.ProductionModuleBlueprintRepository;
import itmo.is.project.repository.module.production.ProductionModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductionModuleService extends
        ModuleService<ProductionModule, ProductionModuleBlueprint, ProductionModuleDto, ProductionModuleBlueprintDto> {

    @Autowired
    public ProductionModuleService(
            StorageService storageService,
            ProductionModuleBlueprintRepository productionModuleBlueprintRepository,
            ProductionModuleBlueprintMapper productionModuleBlueprintMapper,
            ProductionModuleRepository productionModuleRepository,
            ProductionModuleMapper productionModuleMapper
    ) {
        super(
                storageService,
                productionModuleBlueprintRepository,
                productionModuleBlueprintMapper,
                productionModuleRepository,
                productionModuleMapper,
                ProductionModule::new
        );
    }
}
