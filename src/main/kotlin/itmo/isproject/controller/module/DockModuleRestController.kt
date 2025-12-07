package itmo.isproject.controller.module

import itmo.isproject.dto.module.BuildModuleRequest
import itmo.isproject.dto.module.dock.DockModuleBlueprintDto
import itmo.isproject.dto.module.dock.DockModuleDto
import itmo.isproject.dto.module.dock.DockingSpotDto
import itmo.isproject.model.user.User
import itmo.isproject.service.module.DockModuleService
import itmo.isproject.service.module.build.DockModuleBuildService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/modules/dock")
class DockModuleRestController(
    private val dockModuleService: DockModuleService,
    private val dockModuleBuildService: DockModuleBuildService
) {

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping
    fun getAllDockModules(@PageableDefault pageable: Pageable): ResponseEntity<Page<DockModuleDto>> {
        return ResponseEntity.ok(dockModuleService.getAllDockModules(pageable))
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping("/{dockModuleId}")
    fun getDockModuleById(@PathVariable dockModuleId: Int): ResponseEntity<DockModuleDto> {
        return ResponseEntity.ok(dockModuleService.getDockModuleById(dockModuleId))
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping("/build/blueprints")
    fun findAllBlueprints(@PageableDefault pageable: Pageable): ResponseEntity<Page<DockModuleBlueprintDto>> {
        return ResponseEntity.ok(dockModuleBuildService.findAllBlueprints(pageable))
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @PostMapping("/build")
    fun buildModule(@RequestBody buildModuleRequest: BuildModuleRequest): ResponseEntity<DockModuleDto> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(dockModuleBuildService.buildModule(buildModuleRequest))
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping("/spots")
    fun getAllDockingSpots(@PageableDefault pageable: Pageable): ResponseEntity<Page<DockingSpotDto>> {
        return ResponseEntity.ok(dockModuleService.getAllDockingSpots(pageable))
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping("/spots/occupied")
    fun getAllOccupiedDockingSpots(@PageableDefault pageable: Pageable): ResponseEntity<Page<DockingSpotDto>> {
        return ResponseEntity.ok(dockModuleService.getAllOccupiedDockingSpots(pageable))
    }

    @GetMapping("/spots/occupied/my")
    fun getMyDockingSpot(@AuthenticationPrincipal user: User): ResponseEntity<DockingSpotDto> {
        return ResponseEntity.ok(dockModuleService.getOccupiedDockingSpotByPilot(user))
    }

    @PostMapping("/requests/dock")
    fun dock(@AuthenticationPrincipal user: User): ResponseEntity<DockingSpotDto> {
        return ResponseEntity.ok(dockModuleService.dock(user))
    }

    @PostMapping("/requests/undock")
    fun undock(@AuthenticationPrincipal user: User): ResponseEntity<Void> {
        dockModuleService.undock(user)
        return ResponseEntity.ok().build()
    }
}
