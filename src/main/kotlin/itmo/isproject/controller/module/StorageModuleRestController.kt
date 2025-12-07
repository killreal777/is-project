package itmo.isproject.controller.module

import itmo.isproject.dto.module.BuildModuleRequest
import itmo.isproject.dto.module.storage.StorageModuleBlueprintDto
import itmo.isproject.dto.module.storage.StorageModuleDto
import itmo.isproject.dto.module.storage.StoredResourceDto
import itmo.isproject.dto.resource.ResourceAmountDto
import itmo.isproject.service.module.StorageModuleService
import itmo.isproject.service.module.build.StorageModuleBuildService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/modules/storage")
class StorageModuleRestController(
    private val storageModuleService: StorageModuleService,
    private val storageModuleBuildService: StorageModuleBuildService
) {

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ENGINEER')")
    @GetMapping
    fun getAllStorageModules(@PageableDefault pageable: Pageable): ResponseEntity<Page<StorageModuleDto>> {
        return ResponseEntity.ok(storageModuleService.getAllStorageModules(pageable))
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ENGINEER')")
    @GetMapping("/{storageModuleId}")
    fun getStorageModuleById(@PathVariable storageModuleId: Int): ResponseEntity<StorageModuleDto> {
        return ResponseEntity.ok(storageModuleService.getStorageModuleById(storageModuleId))
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping("/build/blueprints")
    fun findAllBlueprints(@PageableDefault pageable: Pageable): ResponseEntity<Page<StorageModuleBlueprintDto>> {
        return ResponseEntity.ok(storageModuleBuildService.findAllBlueprints(pageable))
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @PostMapping("/build")
    fun buildModule(@RequestBody buildModuleRequest: BuildModuleRequest): ResponseEntity<StorageModuleDto> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(storageModuleBuildService.buildModule(buildModuleRequest))
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ENGINEER')")
    @GetMapping("/resources")
    fun findAll(@PageableDefault pageable: Pageable): ResponseEntity<Page<StoredResourceDto>> {
        return ResponseEntity.ok(storageModuleService.getAllStoredResources(pageable))
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ENGINEER')")
    @GetMapping("/resources/{storageId}")
    fun findByStorageId(
        @PathVariable storageId: Int,
        @PageableDefault pageable: Pageable
    ): ResponseEntity<Page<ResourceAmountDto>> {
        return ResponseEntity.ok(storageModuleService.getAllResourcesByStorageId(storageId, pageable))
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ENGINEER')")
    @GetMapping("/resources/total")
    fun findAllTotal(@PageableDefault pageable: Pageable): ResponseEntity<Page<ResourceAmountDto>> {
        return ResponseEntity.ok(storageModuleService.getAllResourcesTotal(pageable))
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ENGINEER')")
    @GetMapping("/resources/{resourceId}/total")
    fun findByResourceId(@PathVariable resourceId: Int): ResponseEntity<ResourceAmountDto> {
        return ResponseEntity.ok(storageModuleService.getResourceAmountTotalByResourceId(resourceId))
    }
}
