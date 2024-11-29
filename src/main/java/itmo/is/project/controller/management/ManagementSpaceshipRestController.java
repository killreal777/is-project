package itmo.is.project.controller.management;

import itmo.is.project.dto.user.SpaceshipDto;
import itmo.is.project.service.user.SpaceshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v3/management/spaceships")
@RequiredArgsConstructor
public class ManagementSpaceshipRestController {
    private final SpaceshipService spaceshipService;

    @GetMapping
    public ResponseEntity<Page<SpaceshipDto>> findAllSpaceships(Pageable pageable) {
        return ResponseEntity.ok(spaceshipService.findAllSpaceships(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpaceshipDto> findSpaceshipById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(spaceshipService.findSpaceshipById(id));
    }
}
