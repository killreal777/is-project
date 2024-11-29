package itmo.is.project.controller.security;

import itmo.is.project.dto.user.UserDto;
import itmo.is.project.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminRestController {
    private final AuthenticationService authenticationService;

    @GetMapping("/register/requests")
    public ResponseEntity<Page<UserDto>> getPendingRegistrationRequests(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(authenticationService.getPendingRegistrationRequests(pageable));
    }

    @PutMapping("/register/requests/{userId}/approve")
    public ResponseEntity<Void> approveRegistrationApplication(@PathVariable Integer userId) {
        authenticationService.approveRegistrationRequest(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/register/requests/{userId}/reject")
    public ResponseEntity<Void> rejectRegistrationApplication(@PathVariable Integer userId) {
        authenticationService.rejectAdminRegistrationRequest(userId);
        return ResponseEntity.noContent().build();
    }
}
