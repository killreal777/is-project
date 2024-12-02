package itmo.is.project.controller.user;

import itmo.is.project.dto.user.CreateSpaceshipRequest;
import itmo.is.project.dto.user.SpaceshipDto;
import itmo.is.project.model.user.User;
import itmo.is.project.service.user.SpaceshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SpaceshipRestController {
    private final SpaceshipService spaceshipService;

    @GetMapping("/spaceships")
    public ResponseEntity<Page<SpaceshipDto>> findAllSpaceships(Pageable pageable) {
        return ResponseEntity.ok(spaceshipService.findAllSpaceships(pageable));
    }

    @GetMapping("/spaceships/{id}")
    public ResponseEntity<SpaceshipDto> findSpaceshipById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(spaceshipService.findSpaceshipById(id));
    }

    @GetMapping("/spaceship")
    public ResponseEntity<SpaceshipDto> getSpaceship(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(spaceshipService.findSpaceshipByPilotId(user.getId()));
    }

    @PostMapping("/spaceship")
    public ResponseEntity<SpaceshipDto> createSpaceship(
            @RequestBody CreateSpaceshipRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(spaceshipService.createSpaceship(request, user));
    }
}
