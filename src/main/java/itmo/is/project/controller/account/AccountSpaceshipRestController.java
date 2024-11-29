package itmo.is.project.controller.account;

import itmo.is.project.dto.user.CreateSpaceshipRequest;
import itmo.is.project.dto.user.SpaceshipDto;
import itmo.is.project.model.user.User;
import itmo.is.project.service.user.SpaceshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v3/account/spaceship")
@RequiredArgsConstructor
public class AccountSpaceshipRestController {
    private final SpaceshipService spaceshipService;

    @GetMapping
    public ResponseEntity<SpaceshipDto> getSpaceship(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(spaceshipService.findSpaceshipByPilotId(user.getId()));
    }

    @PostMapping
    public ResponseEntity<SpaceshipDto> createSpaceship(
            @RequestBody CreateSpaceshipRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(spaceshipService.createSpaceship(request, user));
    }
}
