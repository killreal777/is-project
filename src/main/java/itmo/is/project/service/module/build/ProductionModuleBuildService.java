package itmo.is.project.service.module.build;

import itmo.is.project.dto.module.production.ProductionModuleBlueprintDto;
import itmo.is.project.dto.module.production.ProductionModuleDto;
import itmo.is.project.mapper.module.production.ProductionModuleBlueprintMapper;
import itmo.is.project.mapper.module.production.ProductionModuleMapper;
import itmo.is.project.model.module.production.ProductionModule;
import itmo.is.project.model.module.production.ProductionModuleBlueprint;
import itmo.is.project.repository.module.production.ProductionModuleBlueprintRepository;
import itmo.is.project.repository.module.production.ProductionModuleRepository;
import itmo.is.project.service.module.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductionModuleBuildService extends
        BuildService<ProductionModule, ProductionModuleBlueprint, ProductionModuleDto, ProductionModuleBlueprintDto> {

    @Autowired
    public ProductionModuleBuildService(
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
