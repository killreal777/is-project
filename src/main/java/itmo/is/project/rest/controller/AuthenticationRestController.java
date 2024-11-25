package itmo.is.project.rest.controller;

import itmo.is.project.dto.security.AuthenticationRequest;
import itmo.is.project.dto.security.JwtResponse;
import itmo.is.project.dto.security.RegistrationRequest;
import itmo.is.project.dto.security.UserDto;
import itmo.is.project.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationRestController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/register/owner")
    public ResponseEntity<JwtResponse> registerOwner(@RequestBody RegistrationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.registerOwner(request));
    }

    @PostMapping("/register/pilot")
    public ResponseEntity<JwtResponse> registerPilot(@RequestBody RegistrationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.registerPilot(request));
    }

    @PostMapping("/register/manager")
    public ResponseEntity<Void> registerManager(@RequestBody RegistrationRequest request) {
        authenticationService.submitManagerRegistrationRequest(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/register/engineer")
    public ResponseEntity<Void> registerEngineer(@RequestBody RegistrationRequest request) {
        authenticationService.submitEngineerRegistrationRequest(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/register/requests")
    public ResponseEntity<Page<UserDto>> getPendingRegistrationRequests(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(authenticationService.getPendingRegistrationRequests(pageable));
    }

    @PutMapping("/register/requests/approve/{userId}")
    public ResponseEntity<Void> approveRegistrationApplication(@PathVariable Integer userId) {
        authenticationService.approveRegistrationRequest(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/register/requests/reject/{userId}")
    public ResponseEntity<Void> rejectRegistrationApplication(@PathVariable Integer userId) {
        authenticationService.rejectAdminRegistrationRequest(userId);
        return ResponseEntity.noContent().build();
    }
}
