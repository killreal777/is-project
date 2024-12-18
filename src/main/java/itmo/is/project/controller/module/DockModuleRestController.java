package itmo.is.project.controller.module;

import itmo.is.project.dto.module.BuildModuleRequest;
import itmo.is.project.dto.module.dock.DockModuleBlueprintDto;
import itmo.is.project.dto.module.dock.DockModuleDto;
import itmo.is.project.dto.module.dock.DockingSpotDto;
import itmo.is.project.model.user.User;
import itmo.is.project.service.module.DockModuleService;
import itmo.is.project.service.module.build.DockModuleBuildService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/modules/dock")
@RequiredArgsConstructor
public class DockModuleRestController {
    private final DockModuleService dockModuleService;
    private final DockModuleBuildService dockModuleBuildService;

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping
    public ResponseEntity<Page<DockModuleDto>> getAllDockModules(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(dockModuleService.getAllDockModules(pageable));
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping("/{dockModuleId}")
    public ResponseEntity<DockModuleDto> getDockModuleById(@PathVariable Integer dockModuleId) {
        return ResponseEntity.ok(dockModuleService.getDockModuleById(dockModuleId));
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping("/build/blueprints")
    public ResponseEntity<Page<DockModuleBlueprintDto>> findAllBlueprints(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(dockModuleBuildService.findAllBlueprints(pageable));
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @PostMapping("/build")
    public ResponseEntity<DockModuleDto> buildModule(@RequestBody BuildModuleRequest buildModuleRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dockModuleBuildService.buildModule(buildModuleRequest));
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping("/spots")
    public ResponseEntity<Page<DockingSpotDto>> getAllDockingSpots(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(dockModuleService.getAllDockingSpots(pageable));
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping("/spots/occupied")
    public ResponseEntity<Page<DockingSpotDto>> getAllOccupiedDockingSpots(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(dockModuleService.getAllOccupiedDockingSpots(pageable));
    }

    @GetMapping("/spots/occupied/my")
    public ResponseEntity<DockingSpotDto> getMyDockingSpot(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(dockModuleService.getOccupiedDockingSpotByPilot(user));
    }

    @PostMapping("/requests/dock")
    public ResponseEntity<DockingSpotDto> dock(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(dockModuleService.dock(user));
    }

    @PostMapping("/requests/undock")
    public ResponseEntity<Void> undock(@AuthenticationPrincipal User user) {
        dockModuleService.undock(user);
        return ResponseEntity.ok().build();
    }
}
