package itmo.isproject.controller.user

import itmo.isproject.dto.user.UserDto
import itmo.isproject.mapper.user.UserMapper
import itmo.isproject.model.user.Role
import itmo.isproject.model.user.User
import itmo.isproject.service.user.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class UserRestController(
    private val userService: UserService,
    private val userMapper: UserMapper
) {

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping
    fun getAllUsers(
        @PageableDefault pageable: Pageable,
        @RequestParam(required = false) role: Role?
    ): ResponseEntity<Page<UserDto>> {
        return ResponseEntity.ok(userService.getAllUsers(pageable, role))
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping("/{userId}")
    fun getUserById(@PathVariable userId: Int): ResponseEntity<UserDto> {
        return ResponseEntity.ok(userService.getUserById(userId))
    }

    @GetMapping("/me")
    fun getMe(@AuthenticationPrincipal user: User): ResponseEntity<UserDto> {
        return ResponseEntity.ok(userMapper.toDto(user))
    }
}
