package itmo.isproject.security.service

import itmo.isproject.dto.security.AuthenticationRequest
import itmo.isproject.dto.security.JwtResponse
import itmo.isproject.dto.security.RegistrationRequest
import itmo.isproject.dto.user.UserDto
import itmo.isproject.mapper.user.UserMapper
import itmo.isproject.model.user.Role
import itmo.isproject.model.user.User
import itmo.isproject.service.user.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val userService: UserService,
    private val userMapper: UserMapper,
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService
) {

    fun authenticate(request: AuthenticationRequest): JwtResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.username,
                request.password
            )
        )
        val user = userService.findUserByUsername(request.username)
        validateUserEnabled(user)
        return JwtResponse(jwtService.generateToken(user), userMapper.toDto(user))
    }

    fun registerOwner(request: RegistrationRequest): JwtResponse {
        if (userService.isOwnerRegistered()) {
            throw IllegalStateException("Owner is already registered")
        }
        val enabled = true
        val user = userService.createUser(request, Role.ROLE_OWNER, enabled)
        return JwtResponse(jwtService.generateToken(user), userMapper.toDto(user))
    }

    fun registerPilot(request: RegistrationRequest): JwtResponse {
        val enabled = true
        val user = userService.createUser(request, Role.ROLE_PILOT, enabled)
        return JwtResponse(jwtService.generateToken(user), userMapper.toDto(user))
    }

    fun applyManagerRegistrationRequest(request: RegistrationRequest) {
        val enabled = false
        userService.createUser(request, Role.ROLE_MANAGER, enabled)
    }

    fun applyEngineerRegistrationRequest(request: RegistrationRequest) {
        val enabled = false
        userService.createUser(request, Role.ROLE_ENGINEER, enabled)
    }

    fun getPendingRegistrationRequests(pageable: Pageable): Page<UserDto> {
        return userService.findAllDisabledUsers(pageable).map { userMapper.toDto(it) }
    }

    fun approveRegistrationRequest(userId: Int) {
        val user = userService.findUserById(userId)
        user.enabled = true
        userService.updateUser(user)
    }

    fun rejectAdminRegistrationRequest(userId: Int) {
        val user = userService.findUserById(userId)
        validateUserNotEnabled(user)
        userService.deleteUser(user)
    }

    private fun validateUserEnabled(user: User) {
        if (!user.enabled) {
            throw IllegalStateException("User is disabled: ${user.usernameInternal}")
        }
    }

    private fun validateUserNotEnabled(user: User) {
        if (user.enabled) {
            throw IllegalStateException("User is enabled: ${user.usernameInternal}")
        }
    }
}
