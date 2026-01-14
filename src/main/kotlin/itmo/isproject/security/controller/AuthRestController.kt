package itmo.isproject.security.controller

import itmo.isproject.security.dto.AuthenticationRequest
import itmo.isproject.security.dto.JwtResponse
import itmo.isproject.security.dto.RegistrationRequest
import itmo.isproject.shared.user.dto.UserDto
import itmo.isproject.security.service.AuthService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthRestController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(@RequestBody request: AuthenticationRequest): ResponseEntity<JwtResponse> {
        return ResponseEntity.ok(authService.authenticate(request))
    }

    @PostMapping("/register/owner")
    fun registerOwner(@RequestBody request: RegistrationRequest): ResponseEntity<JwtResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerOwner(request))
    }

    @PostMapping("/register/pilot")
    fun registerPilot(@RequestBody request: RegistrationRequest): ResponseEntity<JwtResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerPilot(request))
    }

    @PostMapping("/register/manager")
    fun registerManager(@RequestBody request: RegistrationRequest): ResponseEntity<Void> {
        authService.applyManagerRegistrationRequest(request)
        return ResponseEntity.accepted().build()
    }

    @PostMapping("/register/engineer")
    fun registerEngineer(@RequestBody request: RegistrationRequest): ResponseEntity<Void> {
        authService.applyEngineerRegistrationRequest(request)
        return ResponseEntity.accepted().build()
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping("/register/requests")
    fun getPendingRegistrationRequests(@PageableDefault pageable: Pageable): ResponseEntity<Page<UserDto>> {
        return ResponseEntity.ok(authService.getPendingRegistrationRequests(pageable))
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @PutMapping("/register/requests/{userId}/approve")
    fun approveRegistrationApplication(@PathVariable userId: Int): ResponseEntity<Void> {
        authService.approveRegistrationRequest(userId)
        return ResponseEntity.ok().build()
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @DeleteMapping("/register/requests/{userId}/reject")
    fun rejectRegistrationApplication(@PathVariable userId: Int): ResponseEntity<Void> {
        authService.rejectAdminRegistrationRequest(userId)
        return ResponseEntity.noContent().build()
    }
}