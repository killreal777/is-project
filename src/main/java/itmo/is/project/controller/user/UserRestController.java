package itmo.is.project.controller.user;

import itmo.is.project.dto.user.UserDto;
import itmo.is.project.mapper.user.UserMapper;
import itmo.is.project.model.user.Role;
import itmo.is.project.model.user.User;
import itmo.is.project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping
    public ResponseEntity<Page<UserDto>> getAllUsers(
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) Role role
    ) {
        return ResponseEntity.ok(userService.getAllUsers(pageable, role));
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getMe(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userMapper.toDto(user));
    }
}
