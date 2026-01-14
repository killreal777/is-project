package itmo.isproject.shared.user.controller

import itmo.isproject.shared.user.dto.spaceship.CreateSpaceshipRequest
import itmo.isproject.shared.user.dto.spaceship.SpaceshipDto
import itmo.isproject.shared.user.dto.spaceship.UpdateSpaceshipRequest
import itmo.isproject.shared.user.model.User
import itmo.isproject.shared.user.service.SpaceshipService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/spaceships")
class SpaceshipRestController(
    private val spaceshipService: SpaceshipService
) {

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping
    fun findAllSpaceships(@PageableDefault pageable: Pageable): ResponseEntity<Page<SpaceshipDto>> {
        return ResponseEntity.ok(spaceshipService.findAllSpaceships(pageable))
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping("/{spaceshipId}")
    fun findSpaceshipById(@PathVariable("spaceshipId") spaceshipId: Int): ResponseEntity<SpaceshipDto> {
        return ResponseEntity.ok(spaceshipService.findSpaceshipById(spaceshipId))
    }

    @GetMapping("/my")
    fun getMySpaceship(@AuthenticationPrincipal user: User): ResponseEntity<SpaceshipDto> {
        return ResponseEntity.ok(spaceshipService.findSpaceshipByPilotId(user.id!!))
    }

    @PostMapping("/my")
    fun createMySpaceship(
        @RequestBody request: CreateSpaceshipRequest,
        @AuthenticationPrincipal user: User
    ): ResponseEntity<SpaceshipDto> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(spaceshipService.createSpaceship(request, user))
    }

    @PutMapping("/my")
    fun updateMySpaceship(
        @RequestBody request: UpdateSpaceshipRequest,
        @AuthenticationPrincipal user: User
    ): ResponseEntity<SpaceshipDto> {
        return ResponseEntity.ok(spaceshipService.updateSpaceship(request, user))
    }
}
