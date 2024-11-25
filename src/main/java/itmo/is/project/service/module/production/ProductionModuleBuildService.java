package itmo.is.project.service.module.production;

import itmo.is.project.dto.ProductionModuleBlueprintDto;
import itmo.is.project.dto.ProductionModuleDto;
import itmo.is.project.dto.request.ModuleBuildRequest;
import itmo.is.project.mapper.module.ProductionModuleBlueprintMapper;
import itmo.is.project.mapper.module.ProductionModuleMapper;
import itmo.is.project.model.module.production.ProductionModule;
import itmo.is.project.model.module.production.ProductionModuleBlueprint;
import itmo.is.project.repository.module.production.ProductionModuleBlueprintRepository;
import itmo.is.project.repository.module.production.ProductionModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductionModuleBuildService {
    private final ProductionModuleRepository productionModuleRepository;
    private final ProductionModuleMapper productionModuleMapper;

    private final ProductionModuleBlueprintRepository productionModuleBlueprintRepository;
    private final ProductionModuleBlueprintMapper productionModuleBlueprintMapper;

    public Page<ProductionModuleBlueprintDto> findAllBlueprints(Pageable pageable) {
        return productionModuleBlueprintRepository.findAll(pageable)
                .map(productionModuleBlueprintMapper::toDto);
    }

    public ProductionModuleDto buildModule(ModuleBuildRequest moduleBuildRequest) {
        Integer blueprintId = moduleBuildRequest.blueprintId();
        ProductionModuleBlueprint blueprint = productionModuleBlueprintRepository.findById(blueprintId).orElseThrow();
        ProductionModule productionModule = new ProductionModule();
        productionModule.setBlueprint(blueprint);
        productionModule = productionModuleRepository.save(productionModule);
        return productionModuleMapper.toDto(productionModule);
    }
}
