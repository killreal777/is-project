package itmo.isproject.service.module

import itmo.isproject.dto.module.production.ProductionModuleDto
import itmo.isproject.mapper.module.production.ProductionModuleMapper
import itmo.isproject.model.module.production.ProductionModule
import itmo.isproject.model.module.production.ProductionModuleState
import itmo.isproject.model.user.Role
import itmo.isproject.repository.module.production.ProductionModuleRepository
import itmo.isproject.service.user.UserService
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductionModuleService(
    private val productionModuleRepository: ProductionModuleRepository,
    private val productionModuleMapper: ProductionModuleMapper,
    private val storageModuleService: StorageModuleService,
    private val userService: UserService
) {

    fun getAllProductionModules(pageable: Pageable): Page<ProductionModuleDto> {
        return productionModuleRepository.findAll(pageable).map { productionModuleMapper.toDto(it) }
    }

    fun getProductionModuleById(id: Int): ProductionModuleDto {
        return productionModuleRepository.findByIdOrNull(id)?.let { productionModuleMapper.toDto(it) }
            ?: throw EntityNotFoundException("Production module not found with id: $id")
    }

    @Transactional
    fun assignEngineer(productionModuleId: Int, userId: Int): ProductionModuleDto {
        var productionModule = findProductionModuleById(productionModuleId)
        if (productionModule.engineer != null) {
            throw IllegalStateException("Engine is already assigned to production module")
        }
        val user = userService.findUserById(userId)
        if (user.role != Role.ROLE_ENGINEER) {
            throw IllegalArgumentException("This user is not an engineer")
        }
        productionModule.engineer = user
        productionModule = productionModuleRepository.save(productionModule)
        return productionModuleMapper.toDto(productionModule)
    }

    @Transactional
    fun removeEngineer(productionModuleId: Int): ProductionModuleDto {
        var productionModule = findProductionModuleById(productionModuleId)
        if (productionModule.state != ProductionModuleState.OFF) {
            throw IllegalStateException("Cannot remove engineer: production module is not off")
        }
        if (productionModule.engineer != null) {
            productionModule.engineer = null
            productionModule = productionModuleRepository.save(productionModule)
        }
        return productionModuleMapper.toDto(productionModule)
    }

    @Transactional
    fun start(productionModuleId: Int): ProductionModuleDto {
        var productionModule = findProductionModuleById(productionModuleId)
        if (productionModule.engineer == null) {
            throw IllegalStateException("Cannot start production module: engineer is not assigned")
        }
        if (productionModule.state == ProductionModuleState.OFF) {
            productionModule.state = ProductionModuleState.READY
            productionModule = productionModuleRepository.save(productionModule)
        }
        return productionModuleMapper.toDto(productionModule)
    }

    @Transactional
    fun stop(productionModuleId: Int): ProductionModuleDto {
        var productionModule = findProductionModuleById(productionModuleId)
        productionModule.state = ProductionModuleState.OFF
        productionModule = productionModuleRepository.save(productionModule)
        return productionModuleMapper.toDto(productionModule)
    }

    @Transactional
    fun loadConsumingResources(productionModuleId: Int): ProductionModuleDto {
        var productionModule = findProductionModuleById(productionModuleId)
        if (productionModule.state != ProductionModuleState.READY) {
            throw IllegalStateException("Cannot load resources: production module is not ready")
        }
        val consumption = productionModule.blueprint!!.consumption
        storageModuleService.retrieveAll(consumption)
        productionModule.state = ProductionModuleState.MANUFACTURING
        productionModule = productionModuleRepository.save(productionModule)
        return productionModuleMapper.toDto(productionModule)
    }

    @Transactional
    fun storeProducedResources(productionModuleId: Int): ProductionModuleDto {
        var productionModule = findProductionModuleById(productionModuleId)
        if (productionModule.state != ProductionModuleState.MANUFACTURING) {
            throw IllegalStateException("Cannot store production module: production module is not manufacturing")
        }
        val production = productionModule.blueprint!!.production!!
        storageModuleService.store(production)
        productionModule.state = ProductionModuleState.READY
        productionModule = productionModuleRepository.save(productionModule)
        return productionModuleMapper.toDto(productionModule)
    }

    private fun findProductionModuleById(productionModuleId: Int): ProductionModule {
        return productionModuleRepository.findByIdOrNull(productionModuleId)
            ?: throw EntityNotFoundException("Production module not found with id: $productionModuleId")
    }
}
