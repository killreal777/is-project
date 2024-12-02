package itmo.is.project.controller.module;

import itmo.is.project.dto.module.BuildModuleRequest;
import itmo.is.project.dto.module.dock.DockModuleBlueprintDto;
import itmo.is.project.dto.module.dock.DockModuleDto;
import itmo.is.project.dto.module.dock.DockingSpotDto;
import itmo.is.project.model.user.User;
import itmo.is.project.service.module.DockService;
import itmo.is.project.service.module.build.DockModuleBuildService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/modules/dock")
@RequiredArgsConstructor
public class DockModuleRestController {
    private final DockService dockService;
    private final DockModuleBuildService dockModuleBuildService;

    @GetMapping("/build/blueprints")
    public ResponseEntity<Page<DockModuleBlueprintDto>> findAllBlueprints(Pageable pageable) {
        return ResponseEntity.ok(dockModuleBuildService.findAllBlueprints(pageable));
    }

    @PostMapping("/build")
    public ResponseEntity<DockModuleDto> buildModule(
            @RequestBody BuildModuleRequest buildModuleRequest
    ) {
        return ResponseEntity.ok(dockModuleBuildService.buildModule(buildModuleRequest));
    }

    @GetMapping("/spots")
    public ResponseEntity<Page<DockingSpotDto>> getAllDockingSpots(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(dockService.getAllDockingSpots(pageable));
    }

    @GetMapping("/spots/occupied")
    public ResponseEntity<Page<DockingSpotDto>> getAllOccupiedDockingSpots(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(dockService.getAllOccupiedDockingSpots(pageable));
    }

    @PostMapping("/requests/dock")
    public ResponseEntity<DockingSpotDto> dock(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(dockService.dock(user));
    }

    @PostMapping("/requests/undock")
    public ResponseEntity<Void> undock(@AuthenticationPrincipal User user) {
        dockService.undock(user);
        return ResponseEntity.ok().build();
    }
}
