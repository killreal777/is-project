package itmo.is.project.controller.user;

import itmo.is.project.dto.user.spaceship.CreateSpaceshipRequest;
import itmo.is.project.dto.user.spaceship.SpaceshipDto;
import itmo.is.project.dto.user.spaceship.UpdateSpaceshipRequest;
import itmo.is.project.model.user.User;
import itmo.is.project.service.user.SpaceshipService;
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
@RequestMapping("/api/v1/spaceships")
@RequiredArgsConstructor
public class SpaceshipRestController {
    private final SpaceshipService spaceshipService;

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping
    public ResponseEntity<Page<SpaceshipDto>> findAllSpaceships(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(spaceshipService.findAllSpaceships(pageable));
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping("/{spaceshipId}")
    public ResponseEntity<SpaceshipDto> findSpaceshipById(@PathVariable("spaceshipId") Integer spaceshipId) {
        return ResponseEntity.ok(spaceshipService.findSpaceshipById(spaceshipId));
    }

    @GetMapping("/my")
    public ResponseEntity<SpaceshipDto> getMySpaceship(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(spaceshipService.findSpaceshipByPilotId(user.getId()));
    }

    @PostMapping("/my")
    public ResponseEntity<SpaceshipDto> createMySpaceship(
            @RequestBody CreateSpaceshipRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(spaceshipService.createSpaceship(request, user));
    }

    @PutMapping("/my")
    public ResponseEntity<SpaceshipDto> updateMySpaceship(
            @RequestBody UpdateSpaceshipRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(spaceshipService.updateSpaceship(request, user));
    }
}
