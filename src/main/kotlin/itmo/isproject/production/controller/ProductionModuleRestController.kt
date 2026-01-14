package itmo.isproject.production.controller

import itmo.isproject.shared.module.dto.BuildModuleRequest
import itmo.isproject.production.dto.ProductionModuleBlueprintDto
import itmo.isproject.production.dto.ProductionModuleDto
import itmo.isproject.production.service.ProductionModuleService
import itmo.isproject.production.service.ProductionModuleBuildService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/modules/production")
class ProductionModuleRestController(
    private val productionModuleService: ProductionModuleService,
    private val productionModuleBuildService: ProductionModuleBuildService
) {

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ENGINEER')")
    @GetMapping
    fun getAllProductionModules(@PageableDefault pageable: Pageable): ResponseEntity<Page<ProductionModuleDto>> {
        return ResponseEntity.ok(productionModuleService.getAllProductionModules(pageable))
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ENGINEER')")
    @GetMapping("/{productionModuleId}")
    fun getProductionModule(@PathVariable productionModuleId: Int): ResponseEntity<ProductionModuleDto> {
        return ResponseEntity.ok(productionModuleService.getProductionModuleById(productionModuleId))
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping("/build/blueprints")
    fun findAllBlueprints(@PageableDefault pageable: Pageable): ResponseEntity<Page<ProductionModuleBlueprintDto>> {
        return ResponseEntity.ok(productionModuleBuildService.findAllBlueprints(pageable))
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @PostMapping("/build")
    fun buildModule(@RequestBody buildModuleRequest: BuildModuleRequest): ResponseEntity<ProductionModuleDto> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(productionModuleBuildService.buildModule(buildModuleRequest))
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @PostMapping("/{productionModuleId}/engineer")
    fun assignEngineer(
        @PathVariable productionModuleId: Int,
        @RequestBody engineerId: Int
    ): ResponseEntity<ProductionModuleDto> {
        return ResponseEntity.ok(productionModuleService.assignEngineer(productionModuleId, engineerId))
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @DeleteMapping("/{productionModuleId}/engineer")
    fun removeEngineer(@PathVariable productionModuleId: Int): ResponseEntity<ProductionModuleDto> {
        return ResponseEntity.ok(productionModuleService.removeEngineer(productionModuleId))
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @PostMapping("/{productionModuleId}/start")
    fun start(@PathVariable productionModuleId: Int): ResponseEntity<ProductionModuleDto> {
        return ResponseEntity.ok(productionModuleService.start(productionModuleId))
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @PostMapping("/{productionModuleId}/stop")
    fun stop(@PathVariable productionModuleId: Int): ResponseEntity<ProductionModuleDto> {
        return ResponseEntity.ok(productionModuleService.stop(productionModuleId))
    }

    @PreAuthorize("hasRole('ROLE_ENGINEER')")
    @PostMapping("/{productionModuleId}/load")
    fun loadConsumingResources(@PathVariable productionModuleId: Int): ResponseEntity<ProductionModuleDto> {
        return ResponseEntity.ok(productionModuleService.loadConsumingResources(productionModuleId))
    }

    @PreAuthorize("hasRole('ROLE_ENGINEER')")
    @PostMapping("/{productionModuleId}/store")
    fun storeProducedResources(@PathVariable productionModuleId: Int): ResponseEntity<ProductionModuleDto> {
        return ResponseEntity.ok(productionModuleService.storeProducedResources(productionModuleId))
    }
}