package itmo.is.project.service.module;

import itmo.is.project.dto.module.production.ProductionModuleDto;
import itmo.is.project.mapper.module.production.ProductionModuleMapper;
import itmo.is.project.model.module.production.Consumption;
import itmo.is.project.model.module.production.Production;
import itmo.is.project.model.module.production.ProductionModule;
import itmo.is.project.model.module.production.ProductionModuleState;
import itmo.is.project.model.user.Role;
import itmo.is.project.model.user.User;
import itmo.is.project.repository.module.production.ProductionModuleRepository;
import itmo.is.project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductionModuleService {
    private final UserService userService;

    private final ProductionModuleRepository productionModuleRepository;
    private final ProductionModuleMapper productionModuleMapper;

    private final StorageModuleService storageModuleService;

    @Transactional
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

    @Transactional
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

    @Transactional
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

    @Transactional
    public ProductionModuleDto stop(Integer productionModuleId) {
        ProductionModule productionModule = findProductionModuleById(productionModuleId);
        productionModule.setState(ProductionModuleState.OFF);
        productionModule = productionModuleRepository.save(productionModule);
        return productionModuleMapper.toDto(productionModule);
    }

    @Transactional
    public ProductionModuleDto loadConsumingResources(Integer productionModuleId) {
        ProductionModule productionModule = findProductionModuleById(productionModuleId);
        if (productionModule.getState() != ProductionModuleState.READY) {
            throw new IllegalStateException();
        }
        List<Consumption> consumption = productionModule.getBlueprint().getConsumption();
        storageModuleService.retrieveAllResources(consumption);
        productionModule.setState(ProductionModuleState.MANUFACTURING);
        productionModule = productionModuleRepository.save(productionModule);
        return productionModuleMapper.toDto(productionModule);
    }

    @Transactional
    public ProductionModuleDto storeProducedResources(Integer productionModuleId) {
        ProductionModule productionModule = findProductionModuleById(productionModuleId);
        if (productionModule.getState() != ProductionModuleState.MANUFACTURING) {
            throw new IllegalStateException();
        }
        Production production = productionModule.getBlueprint().getProduction();
        storageModuleService.storeResource(production);
        productionModule.setState(ProductionModuleState.READY);
        productionModule = productionModuleRepository.save(productionModule);
        return productionModuleMapper.toDto(productionModule);
    }

    private ProductionModule findProductionModuleById(Integer productionModuleId) {
        return productionModuleRepository.findById(productionModuleId).orElseThrow();
    }
}
