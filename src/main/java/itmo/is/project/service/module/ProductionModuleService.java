package itmo.is.project.service.module;

import itmo.is.project.dto.module.production.ProductionModuleBlueprintDto;
import itmo.is.project.dto.module.production.ProductionModuleDto;
import itmo.is.project.mapper.module.production.ProductionModuleBlueprintMapper;
import itmo.is.project.mapper.module.production.ProductionModuleMapper;
import itmo.is.project.model.module.production.*;
import itmo.is.project.model.user.Role;
import itmo.is.project.model.user.User;
import itmo.is.project.repository.module.production.ProductionModuleBlueprintRepository;
import itmo.is.project.repository.module.production.ProductionModuleRepository;
import itmo.is.project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionModuleService extends
        ModuleService<ProductionModule, ProductionModuleBlueprint, ProductionModuleDto, ProductionModuleBlueprintDto> {

    private final UserService userService;

    @Autowired
    public ProductionModuleService(
            StorageService storageService,
            ProductionModuleBlueprintRepository productionModuleBlueprintRepository,
            ProductionModuleBlueprintMapper productionModuleBlueprintMapper,
            ProductionModuleRepository productionModuleRepository,
            ProductionModuleMapper productionModuleMapper,
            UserService userService) {
        super(
                storageService,
                productionModuleBlueprintRepository,
                productionModuleBlueprintMapper,
                productionModuleRepository,
                productionModuleMapper,
                ProductionModule::new
        );
        this.userService = userService;
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
        productionModule = moduleRepository.save(productionModule);
        return moduleMapper.toDto(productionModule);
    }

    public ProductionModuleDto removeEngineer(Integer productionModuleId) {
        ProductionModule productionModule = findProductionModuleById(productionModuleId);
        if (productionModule.getState() != ProductionModuleState.OFF) {
            throw new IllegalStateException();
        }
        if (productionModule.getEngineer() != null) {
            productionModule.setEngineer(null);
            productionModule = moduleRepository.save(productionModule);
        }
        return moduleMapper.toDto(productionModule);
    }

    public ProductionModuleDto start(Integer productionModuleId) {
        ProductionModule productionModule = findProductionModuleById(productionModuleId);
        if (productionModule.getEngineer() == null) {
            throw new IllegalStateException();
        }
        if (productionModule.getState() == ProductionModuleState.OFF) {
            productionModule.setState(ProductionModuleState.READY);
            productionModule = moduleRepository.save(productionModule);
        }
        return moduleMapper.toDto(productionModule);
    }

    public ProductionModuleDto stop(Integer productionModuleId) {
        ProductionModule productionModule = findProductionModuleById(productionModuleId);
        productionModule.setState(ProductionModuleState.OFF);
        productionModule = moduleRepository.save(productionModule);
        return moduleMapper.toDto(productionModule);
    }

    public ProductionModuleDto loadConsumingResources(Integer productionModuleId) {
        ProductionModule productionModule = findProductionModuleById(productionModuleId);
        if (productionModule.getState() != ProductionModuleState.READY) {
            throw new IllegalStateException();
        }
        List<Consumption> consumption = productionModule.getBlueprint().getConsumption();
        storageService.retrieveAll(consumption);
        productionModule.setState(ProductionModuleState.MANUFACTURING);
        productionModule = moduleRepository.save(productionModule);
        return moduleMapper.toDto(productionModule);
    }

    public ProductionModuleDto storeProducedResources(Integer productionModuleId) {
        ProductionModule productionModule = findProductionModuleById(productionModuleId);
        if (productionModule.getState() != ProductionModuleState.MANUFACTURING) {
            throw new IllegalStateException();
        }
        Production production = productionModule.getBlueprint().getProduction();
        storageService.store(production);
        productionModule.setState(ProductionModuleState.READY);
        productionModule = moduleRepository.save(productionModule);
        return moduleMapper.toDto(productionModule);
    }

    private ProductionModule findProductionModuleById(Integer productionModuleId) {
        return moduleRepository.findById(productionModuleId).orElseThrow();
    }
}
