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
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductionModuleService {

    private final ProductionModuleRepository productionModuleRepository;
    private final ProductionModuleMapper productionModuleMapper;

    private final StorageModuleService storageModuleService;
    private final UserService userService;

    public Page<ProductionModuleDto> getAllProductionModules(Pageable pageable) {
        return productionModuleRepository.findAll(pageable).map(productionModuleMapper::toDto);
    }

    public ProductionModuleDto getProductionModuleById(Integer id) {
        return productionModuleRepository.findById(id).map(productionModuleMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Production module not found with id: " + id));
    }

    @Transactional
    public ProductionModuleDto assignEngineer(Integer productionModuleId, Integer userId) {
        ProductionModule productionModule = findProductionModuleById(productionModuleId);
        if (productionModule.getEngineer() != null) {
            throw new IllegalStateException("Engine is already assigned to production module");
        }
        User user = userService.findUserById(userId);
        if (user.getRole() != Role.ROLE_ENGINEER) {
            throw new IllegalArgumentException("This user is not an engineer");
        }
        productionModule.setEngineer(user);
        productionModule = productionModuleRepository.save(productionModule);
        return productionModuleMapper.toDto(productionModule);
    }

    @Transactional
    public ProductionModuleDto removeEngineer(Integer productionModuleId) {
        ProductionModule productionModule = findProductionModuleById(productionModuleId);
        if (productionModule.getState() != ProductionModuleState.OFF) {
            throw new IllegalStateException("Cannot remove engineer: production module is not off");
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
            throw new IllegalStateException("Cannot start production module: engineer is not assigned");
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
            throw new IllegalStateException("Cannot load resources: production module is not ready");
        }
        List<Consumption> consumption = productionModule.getBlueprint().getConsumption();
        storageModuleService.retrieveAll(consumption);
        productionModule.setState(ProductionModuleState.MANUFACTURING);
        productionModule = productionModuleRepository.save(productionModule);
        return productionModuleMapper.toDto(productionModule);
    }

    @Transactional
    public ProductionModuleDto storeProducedResources(Integer productionModuleId) {
        ProductionModule productionModule = findProductionModuleById(productionModuleId);
        if (productionModule.getState() != ProductionModuleState.MANUFACTURING) {
            throw new IllegalStateException("Cannot store production module: production module is not manufacturing");
        }
        Production production = productionModule.getBlueprint().getProduction();
        storageModuleService.store(production);
        productionModule.setState(ProductionModuleState.READY);
        productionModule = productionModuleRepository.save(productionModule);
        return productionModuleMapper.toDto(productionModule);
    }

    private ProductionModule findProductionModuleById(Integer productionModuleId) {
        return productionModuleRepository.findById(productionModuleId).orElseThrow(() ->
                new EntityNotFoundException("Production module not found with id: " + productionModuleId)
        );
    }
}
