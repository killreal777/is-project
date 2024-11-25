package itmo.is.project.rest.controller.security;

import itmo.is.project.dto.security.AuthenticationRequest;
import itmo.is.project.dto.security.JwtResponse;
import itmo.is.project.dto.security.RegistrationRequest;
import itmo.is.project.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
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
        authenticationService.applyManagerRegistrationRequest(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/register/engineer")
    public ResponseEntity<Void> registerEngineer(@RequestBody RegistrationRequest request) {
        authenticationService.applyEngineerRegistrationRequest(request);
        return ResponseEntity.accepted().build();
    }
}
