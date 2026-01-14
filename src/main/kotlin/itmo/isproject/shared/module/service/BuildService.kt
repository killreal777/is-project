package itmo.isproject.shared.module.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.withLoggingContext
import itmo.isproject.shared.module.dto.BuildModuleRequest
import itmo.isproject.shared.common.mapper.EntityMapper
import itmo.isproject.shared.module.model.Module
import itmo.isproject.shared.module.model.ModuleBlueprint
import itmo.isproject.shared.module.repository.ModuleBlueprintRepository
import itmo.isproject.shared.module.repository.ModuleRepository
import itmo.isproject.storage.api.StorageService
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}

abstract class BuildService<M : Module<B>, B : ModuleBlueprint, ModuleDto : Any, BlueprintDto : Any>(
    protected val storageService: StorageService,
    protected val moduleBlueprintRepository: ModuleBlueprintRepository<B>,
    protected val moduleBlueprintMapper: EntityMapper<BlueprintDto, B>,
    protected val moduleRepository: ModuleRepository<M>,
    protected val moduleMapper: EntityMapper<ModuleDto, M>,
    private val moduleConstructor: () -> M
) {

    fun findAllBlueprints(pageable: Pageable): Page<BlueprintDto> {
        withLoggingContext("page" to pageable.pageNumber.toString()) {
            logger.debug { "Fetching all blueprints" }
        }
        return moduleBlueprintRepository.findAll(pageable)
            .map { moduleBlueprintMapper.toDto(it) }
    }

    @Transactional
    open fun buildModule(request: BuildModuleRequest): ModuleDto {
        withLoggingContext("blueprintId" to request.blueprintId.toString()) {
            logger.info { "Building module from blueprint" }
        }
        val blueprint = moduleBlueprintRepository.findByIdOrNull(request.blueprintId)
            ?: throw EntityNotFoundException("Blueprint not found with id: ${request.blueprintId}")
        var module = moduleConstructor()
        module.blueprint = blueprint
        withLoggingContext(
            "blueprintId" to request.blueprintId.toString(),
            "itemsCount" to (blueprint.buildCost?.items?.size?.toString() ?: "null")
        ) {
            logger.debug { "Retrieving build cost resources" }
        }
        storageService.retrieveAll(blueprint.buildCost?.items!!)
        module = moduleRepository.save(module)
        withLoggingContext(
            "moduleId" to module.id.toString(),
            "blueprintId" to request.blueprintId.toString()
        ) {
            logger.info { "Module built successfully" }
        }
        return moduleMapper.toDto(module)
    }
}
