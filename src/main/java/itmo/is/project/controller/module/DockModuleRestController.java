package itmo.is.project.controller.module;

import itmo.is.project.dto.module.dock.DockModuleBlueprintDto;
import itmo.is.project.dto.module.dock.DockModuleDto;
import itmo.is.project.dto.module.dock.DockingSpotDto;
import itmo.is.project.model.user.User;
import itmo.is.project.service.module.DockModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/modules/dock")
public class DockModuleRestController extends ModuleRestController<DockModuleDto, DockModuleBlueprintDto> {
    private final DockModuleService dockModuleService;

    @Autowired
    public DockModuleRestController(DockModuleService dockModuleService) {
        super(dockModuleService);
        this.dockModuleService = dockModuleService;
    }

    @GetMapping("/spots")
    public ResponseEntity<Page<DockingSpotDto>> getAllDockingSpots(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(dockModuleService.getAllDockingSpots(pageable));
    }

    @GetMapping("/spots/occupied")
    public ResponseEntity<Page<DockingSpotDto>> getAllOccupiedDockingSpots(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(dockModuleService.getAllOccupiedDockingSpots(pageable));
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
