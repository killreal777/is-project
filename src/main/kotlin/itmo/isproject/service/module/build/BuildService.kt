package itmo.isproject.service.module.build

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.withLoggingContext
import itmo.isproject.dto.module.BuildModuleRequest
import itmo.isproject.mapper.EntityMapper
import itmo.isproject.model.module.Module
import itmo.isproject.model.module.ModuleBlueprint
import itmo.isproject.repository.module.ModuleBlueprintRepository
import itmo.isproject.repository.module.ModuleRepository
import itmo.isproject.service.module.StorageModuleService
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}

abstract class BuildService<M : Module<B>, B : ModuleBlueprint, ModuleDto, BlueprintDto>(
    protected val storageModuleService: StorageModuleService,
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
        storageModuleService.retrieveAll(blueprint.buildCost?.items!!)
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
