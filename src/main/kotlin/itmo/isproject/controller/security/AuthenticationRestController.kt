package itmo.isproject.controller.security

import itmo.isproject.dto.security.AuthenticationRequest
import itmo.isproject.dto.security.JwtResponse
import itmo.isproject.dto.security.RegistrationRequest
import itmo.isproject.dto.user.UserDto
import itmo.isproject.security.service.AuthenticationService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationRestController(
    private val authenticationService: AuthenticationService
) {

    @PostMapping("/login")
    fun login(@RequestBody request: AuthenticationRequest): ResponseEntity<JwtResponse> {
        return ResponseEntity.ok(authenticationService.authenticate(request))
    }

    @PostMapping("/register/owner")
    fun registerOwner(@RequestBody request: RegistrationRequest): ResponseEntity<JwtResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.registerOwner(request))
    }

    @PostMapping("/register/pilot")
    fun registerPilot(@RequestBody request: RegistrationRequest): ResponseEntity<JwtResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.registerPilot(request))
    }

    @PostMapping("/register/manager")
    fun registerManager(@RequestBody request: RegistrationRequest): ResponseEntity<Void> {
        authenticationService.applyManagerRegistrationRequest(request)
        return ResponseEntity.accepted().build()
    }

    @PostMapping("/register/engineer")
    fun registerEngineer(@RequestBody request: RegistrationRequest): ResponseEntity<Void> {
        authenticationService.applyEngineerRegistrationRequest(request)
        return ResponseEntity.accepted().build()
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping("/register/requests")
    fun getPendingRegistrationRequests(@PageableDefault pageable: Pageable): ResponseEntity<Page<UserDto>> {
        return ResponseEntity.ok(authenticationService.getPendingRegistrationRequests(pageable))
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @PutMapping("/register/requests/{userId}/approve")
    fun approveRegistrationApplication(@PathVariable userId: Int): ResponseEntity<Void> {
        authenticationService.approveRegistrationRequest(userId)
        return ResponseEntity.ok().build()
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @DeleteMapping("/register/requests/{userId}/reject")
    fun rejectRegistrationApplication(@PathVariable userId: Int): ResponseEntity<Void> {
        authenticationService.rejectAdminRegistrationRequest(userId)
        return ResponseEntity.noContent().build()
    }
}
