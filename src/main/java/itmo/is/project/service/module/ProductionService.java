package itmo.is.project.service.module;

import itmo.is.project.dto.module.production.ProductionModuleBlueprintDto;
import itmo.is.project.dto.module.production.ProductionModuleDto;
import itmo.is.project.mapper.module.production.ProductionModuleBlueprintMapper;
import itmo.is.project.mapper.module.production.ProductionModuleMapper;
import itmo.is.project.model.module.production.Consumption;
import itmo.is.project.model.module.production.Production;
import itmo.is.project.model.module.production.ProductionModule;
import itmo.is.project.model.module.production.ProductionModuleState;
import itmo.is.project.model.user.Role;
import itmo.is.project.model.user.User;
import itmo.is.project.repository.module.production.ProductionModuleBlueprintRepository;
import itmo.is.project.repository.module.production.ProductionModuleRepository;
import itmo.is.project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductionService {
    private final UserService userService;

    private final ProductionModuleRepository productionModuleRepository;
    private final ProductionModuleMapper productionModuleMapper;

    private final ProductionModuleBlueprintRepository productionModuleBlueprintRepository;
    private final ProductionModuleBlueprintMapper productionModuleBlueprintMapper;

    private final StorageService storageService;

    public Page<ProductionModuleBlueprintDto> findAllBlueprints(Pageable pageable) {
        return productionModuleBlueprintRepository.findAll(pageable)
                .map(productionModuleBlueprintMapper::toDto);
    }

    public Page<ProductionModuleDto> findAllModules(Pageable pageable) {
        return productionModuleRepository.findAll(pageable).map(productionModuleMapper::toDto);
    }

    public ProductionModuleDto assignEngineer(Integer productionModuleId, Integer userId) {
        ProductionModule productionModule = findProductionModuleById(productionModuleId);
        if (productionModule.getEngineer() != null) {
            throw new IllegalStateException();
        }
        User user = userService.findUserById(userId);
        if (user.getRole() != Role.ROLE_ENGINEER) {
            throw new IllegalArgumentException();
        }
        productionModule.setEngineer(user);
        productionModule = productionModuleRepository.save(productionModule);
        return productionModuleMapper.toDto(productionModule);
    }

    public ProductionModuleDto removeEngineer(Integer productionModuleId) {
        ProductionModule productionModule = findProductionModuleById(productionModuleId);
        if (productionModule.getState() != ProductionModuleState.OFF) {
            throw new IllegalStateException();
        }
        if (productionModule.getEngineer() != null) {
            productionModule.setEngineer(null);
            productionModule = productionModuleRepository.save(productionModule);
        }
        return productionModuleMapper.toDto(productionModule);
    }

    public ProductionModuleDto start(Integer productionModuleId) {
        ProductionModule productionModule = findProductionModuleById(productionModuleId);
        if (productionModule.getEngineer() == null) {
            throw new IllegalStateException();
        }
        if (productionModule.getState() == ProductionModuleState.OFF) {
            productionModule.setState(ProductionModuleState.READY);
            productionModule = productionModuleRepository.save(productionModule);
        }
        return productionModuleMapper.toDto(productionModule);
    }

    public ProductionModuleDto stop(Integer productionModuleId) {
        ProductionModule productionModule = findProductionModuleById(productionModuleId);
        productionModule.setState(ProductionModuleState.OFF);
        productionModule = productionModuleRepository.save(productionModule);
        return productionModuleMapper.toDto(productionModule);
    }

    public ProductionModuleDto loadConsumingResources(Integer productionModuleId) {
        ProductionModule productionModule = findProductionModuleById(productionModuleId);
        if (productionModule.getState() != ProductionModuleState.READY) {
            throw new IllegalStateException();
        }
        List<Consumption> consumption = productionModule.getBlueprint().getConsumption();
        storageService.retrieveAll(consumption);
        productionModule.setState(ProductionModuleState.MANUFACTURING);
        productionModule = productionModuleRepository.save(productionModule);
        return productionModuleMapper.toDto(productionModule);
    }

    public ProductionModuleDto storeProducedResources(Integer productionModuleId) {
        ProductionModule productionModule = findProductionModuleById(productionModuleId);
        if (productionModule.getState() != ProductionModuleState.MANUFACTURING) {
            throw new IllegalStateException();
        }
        Production production = productionModule.getBlueprint().getProduction();
        storageService.store(production);
        productionModule.setState(ProductionModuleState.READY);
        productionModule = productionModuleRepository.save(productionModule);
        return productionModuleMapper.toDto(productionModule);
    }

    private ProductionModule findProductionModuleById(Integer productionModuleId) {
        return productionModuleRepository.findById(productionModuleId).orElseThrow();
    }
}
