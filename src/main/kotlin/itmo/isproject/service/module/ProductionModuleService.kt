package itmo.isproject.service.module

import itmo.isproject.dto.module.production.ProductionModuleDto
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.withLoggingContext
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

private val logger = KotlinLogging.logger {}

@Service
class ProductionModuleService(
    private val productionModuleRepository: ProductionModuleRepository,
    private val productionModuleMapper: ProductionModuleMapper,
    private val storageModuleService: StorageModuleService,
    private val userService: UserService
) {

    fun getAllProductionModules(pageable: Pageable): Page<ProductionModuleDto> {
        withLoggingContext("page" to pageable.pageNumber.toString(), "size" to pageable.pageSize.toString()) {
            logger.debug { "Fetching all production modules" }
        }
        return productionModuleRepository.findAll(pageable).map { productionModuleMapper.toDto(it) }
    }

    fun getProductionModuleById(id: Int): ProductionModuleDto {
        withLoggingContext("productionModuleId" to id.toString()) {
            logger.debug { "Fetching production module by" }
        }
        return productionModuleRepository.findByIdOrNull(id)?.let { productionModuleMapper.toDto(it) }
            ?: throw EntityNotFoundException("Production module not found with id: $id")
    }

    @Transactional
    fun assignEngineer(productionModuleId: Int, userId: Int): ProductionModuleDto {
        withLoggingContext(
            "userId" to userId.toString(),
            "productionModuleId" to productionModuleId.toString()
        ) {
            logger.info { "Assigning engineer" }
        }
        var productionModule = findProductionModuleById(productionModuleId)
        if (productionModule.engineer != null) {
            withLoggingContext("productionModuleId" to productionModuleId.toString()) {
                logger.warn { "Cannot assign engineer: Engineer already assigned to production module" }
            }
            throw IllegalStateException("Engine is already assigned to production module")
        }
        val user = userService.findUserById(userId)
        if (user.role != Role.ROLE_ENGINEER) {
            withLoggingContext(
                "username" to user.usernameInternal,
                "userId" to userId.toString(),
                "role" to user.role.toString()
            ) {
                logger.warn { "Cannot assign engineer: User is not an engineer" }
            }
            throw IllegalArgumentException("This user is not an engineer")
        }
        productionModule.engineer = user
        productionModule = productionModuleRepository.save(productionModule)
        withLoggingContext(
            "username" to user.usernameInternal,
            "productionModuleId" to productionModuleId.toString()
        ) {
            logger.info { "Engineer assigned to production module" }
        }
        return productionModuleMapper.toDto(productionModule)
    }

    @Transactional
    fun removeEngineer(productionModuleId: Int): ProductionModuleDto {
        withLoggingContext("productionModuleId" to productionModuleId.toString()) {
            logger.info { "Removing engineer from production module" }
        }
        var productionModule = findProductionModuleById(productionModuleId)
        if (productionModule.state != ProductionModuleState.OFF) {
            withLoggingContext(
                "productionModuleId" to productionModuleId.toString(),
                "state" to productionModule.state.toString()
            ) {
                logger.warn { "Cannot remove engineer: Production module is not off" }
            }
            throw IllegalStateException("Cannot remove engineer: production module is not off")
        }
        if (productionModule.engineer != null) {
            val engineerName = productionModule.engineer!!.usernameInternal
            productionModule.engineer = null
            productionModule = productionModuleRepository.save(productionModule)
            withLoggingContext(
                "username" to engineerName,
                "productionModuleId" to productionModuleId.toString()
            ) {
                logger.info { "Engineer removed from production module" }
            }
        }
        return productionModuleMapper.toDto(productionModule)
    }

    @Transactional
    fun start(productionModuleId: Int): ProductionModuleDto {
        withLoggingContext("productionModuleId" to productionModuleId.toString()) {
            logger.info { "Starting production module" }
        }
        var productionModule = findProductionModuleById(productionModuleId)
        if (productionModule.engineer == null) {
            withLoggingContext("productionModuleId" to productionModuleId.toString()) {
                logger.warn { "Cannot start production module - engineer is not assigned" }
            }
            throw IllegalStateException("Cannot start production module: engineer is not assigned")
        }
        if (productionModule.state == ProductionModuleState.OFF) {
            productionModule.state = ProductionModuleState.READY
            productionModule = productionModuleRepository.save(productionModule)
            withLoggingContext(
                "productionModuleId" to productionModuleId.toString(),
                "state" to ProductionModuleState.READY.toString()
            ) {
                logger.info { "Production module started" }
            }
        }
        return productionModuleMapper.toDto(productionModule)
    }

    @Transactional
    fun stop(productionModuleId: Int): ProductionModuleDto {
        withLoggingContext("productionModuleId" to productionModuleId.toString()) {
            logger.info { "Stopping production module" }
        }
        var productionModule = findProductionModuleById(productionModuleId)
        productionModule.state = ProductionModuleState.OFF
        productionModule = productionModuleRepository.save(productionModule)
        withLoggingContext(
            "productionModuleId" to productionModuleId.toString(),
            "state" to ProductionModuleState.OFF.toString()
        ) {
            logger.info { "Production module stopped" }
        }
        return productionModuleMapper.toDto(productionModule)
    }

    @Transactional
    fun loadConsumingResources(productionModuleId: Int): ProductionModuleDto {
        withLoggingContext("productionModuleId" to productionModuleId.toString()) {
            logger.info { "Loading consuming resources for production module" }
        }
        var productionModule = findProductionModuleById(productionModuleId)
        if (productionModule.state != ProductionModuleState.READY) {
            withLoggingContext(
                "productionModuleId" to productionModuleId.toString(),
                "state" to productionModule.state.toString()
            ) {
                logger.warn { "Cannot load resources: Production module is not ready" }
            }
            throw IllegalStateException("Cannot load resources: production module is not ready")
        }
        val consumption = productionModule.blueprint!!.consumption
        withLoggingContext(
            "consumptionCount" to consumption.size.toString(),
            "productionModuleId" to productionModuleId.toString()
        ) {
            logger.debug { "Retrieving consuming resources from storage for production module" }
        }
        storageModuleService.retrieveAll(consumption)
        productionModule.state = ProductionModuleState.MANUFACTURING
        productionModule = productionModuleRepository.save(productionModule)
        withLoggingContext(
            "productionModuleId" to productionModuleId.toString(),
            "state" to ProductionModuleState.MANUFACTURING.toString()
        ) {
            logger.info { "Resources loaded, production module state changed to MANUFACTURING" }
        }
        return productionModuleMapper.toDto(productionModule)
    }

    @Transactional
    fun storeProducedResources(productionModuleId: Int): ProductionModuleDto {
        withLoggingContext("productionModuleId" to productionModuleId.toString()) {
            logger.info { "Storing produced resources for production module" }
        }
        var productionModule = findProductionModuleById(productionModuleId)
        if (productionModule.state != ProductionModuleState.MANUFACTURING) {
            withLoggingContext(
                "productionModuleId" to productionModuleId.toString(),
                "state" to productionModule.state.toString()
            ) {
                logger.warn { "Cannot store resources: Production module is not manufacturing" }
            }
            throw IllegalStateException("Cannot store production module: production module is not manufacturing")
        }
        val production = productionModule.blueprint!!.production!!
        withLoggingContext("productionModuleId" to productionModuleId.toString()) {
            logger.debug { "Storing produced resources to storage for production module" }
        }
        storageModuleService.store(production)
        productionModule.state = ProductionModuleState.READY
        productionModule = productionModuleRepository.save(productionModule)
        withLoggingContext(
            "productionModuleId" to productionModuleId.toString(),
            "state" to ProductionModuleState.READY.toString()
        ) {
            logger.info { "Produced resources stored, production module state changed to READY" }
        }
        return productionModuleMapper.toDto(productionModule)
    }

    private fun findProductionModuleById(productionModuleId: Int): ProductionModule {
        return productionModuleRepository.findByIdOrNull(productionModuleId)
            ?: throw EntityNotFoundException("Production module not found with id: $productionModuleId")
    }
}
