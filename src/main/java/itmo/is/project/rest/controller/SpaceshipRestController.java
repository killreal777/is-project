package itmo.is.project.rest.controller;

import itmo.is.project.dto.user.CreateSpaceshipRequest;
import itmo.is.project.dto.user.SpaceshipDto;
import itmo.is.project.model.user.Spaceship;
import itmo.is.project.service.user.SpaceshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v3/users")
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

    @GetMapping("/{username}/spaceship")
    public ResponseEntity<SpaceshipDto> findSpaceshipByPilotId(@PathVariable("username") String username) {
        return ResponseEntity.ok(spaceshipService.findSpaceshipByPilotUsername(username));
    }

//    @PostMapping("/{username}/spaceship")
//    public ResponseEntity<SpaceshipDto> createOwnedSpaceship(
//            @PathVariable("username") String username,
//            @RequestBody CreateSpaceshipRequest request
//    ) {
//        return null;
//    }
}
