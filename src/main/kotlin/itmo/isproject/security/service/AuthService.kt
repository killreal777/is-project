package itmo.isproject.security.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.withLoggingContext
import itmo.isproject.security.dto.AuthenticationRequest
import itmo.isproject.security.dto.JwtResponse
import itmo.isproject.security.dto.RegistrationRequest
import itmo.isproject.shared.user.dto.UserDto
import itmo.isproject.shared.user.mapper.UserMapper
import itmo.isproject.shared.user.model.Role
import itmo.isproject.shared.user.model.User
import itmo.isproject.shared.user.service.UserService
import itmo.isproject.security.mapper.AuthUserMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class AuthService(
    private val userService: UserService,
    private val userMapper: UserMapper,
    private val authUserMapper: AuthUserMapper,
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder
) {

    fun authenticate(request: AuthenticationRequest): JwtResponse {
        withLoggingContext("username" to request.username) {
            logger.info { "Authentication attempt" }
        }
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.username,
                request.password
            )
        )
        val user = userService.findUserByUsername(request.username)
        validateUserEnabled(user)
        withLoggingContext("username" to request.username, "role" to (user.role?.name ?: "null")) {
            logger.info { "User authenticated" }
        }
        return JwtResponse(jwtService.generateToken(user), userMapper.toDto(user))
    }

    fun registerOwner(request: RegistrationRequest): JwtResponse {
        withLoggingContext("username" to request.username) {
            logger.info { "Owner registration attempt" }
        }
        if (userService.isOwnerRegistered()) {
            logger.warn { "Owner registration rejected: Owner already registered" }
            throw IllegalStateException("Owner is already registered")
        }
        val enabled = true
        val user = userService.createUser(request, Role.ROLE_OWNER, enabled)
        withLoggingContext("username" to request.username) {
            logger.info { "Owner registered" }
        }
        return JwtResponse(jwtService.generateToken(user), userMapper.toDto(user))
    }

    fun registerPilot(request: RegistrationRequest): JwtResponse {
        withLoggingContext("username" to request.username) {
            logger.info { "Pilot registration" }
        }
        val enabled = true
        val user = userService.createUser(request, Role.ROLE_PILOT, enabled)
        withLoggingContext("username" to request.username) {
            logger.info { "Pilot registered" }
        }
        return JwtResponse(jwtService.generateToken(user), userMapper.toDto(user))
    }

    fun applyManagerRegistrationRequest(request: RegistrationRequest) {
        withLoggingContext("username" to request.username) {
            logger.info { "Manager registration request submitted" }
        }
        val enabled = false
        userService.createUser(request, Role.ROLE_MANAGER, enabled)
    }

    fun applyEngineerRegistrationRequest(request: RegistrationRequest) {
        withLoggingContext("username" to request.username) {
            logger.info { "Engineer registration request submitted" }
        }
        val enabled = false
        userService.createUser(request, Role.ROLE_ENGINEER, enabled)
    }

    fun getPendingRegistrationRequests(pageable: Pageable): Page<UserDto> {
        withLoggingContext("page" to pageable.pageNumber.toString(), "size" to pageable.pageSize.toString()) {
            logger.debug { "Fetching pending registration requests" }
        }
        return userService.findAllDisabledUsers(pageable).map { userMapper.toDto(it) }
    }

    fun approveRegistrationRequest(userId: Int) {
        withLoggingContext("userId" to userId.toString()) {
            logger.info { "Approving registration request" }
        }
        val user = userService.findUserById(userId)
        user.enabled = true
        userService.updateUser(user)
        withLoggingContext("username" to user.usernameInternal, "role" to (user.role?.name ?: "null")) {
            logger.info { "Registration request approved" }
        }
    }

    fun rejectAdminRegistrationRequest(userId: Int) {
        withLoggingContext("userId" to userId.toString()) {
            logger.info { "Rejecting registration request" }
        }
        val user = userService.findUserById(userId)
        validateUserNotEnabled(user)
        userService.deleteUser(user)
        withLoggingContext("username" to user.usernameInternal) {
            logger.info { "Registration request rejected and user deleted" }
        }
    }

    private fun validateUserEnabled(user: User) {
        if (!user.enabled) {
            withLoggingContext("username" to user.usernameInternal) {
                logger.warn { "Authentication failed: User is disabled" }
            }
            throw IllegalStateException("User is disabled: ${user.usernameInternal}")
        }
    }

    private fun validateUserNotEnabled(user: User) {
        if (user.enabled) {
            withLoggingContext("username" to user.usernameInternal) {
                logger.error { "Cannot reject enabled user" }
            }
            throw IllegalStateException("User is enabled: ${user.usernameInternal}")
        }
    }

    private fun UserService.createUser(request: RegistrationRequest, role: Role, enabled: Boolean): User {
        val user = authUserMapper.toEntity(request).also {
            it.passwordInternal = passwordEncoder.encode(it.passwordInternal)
            it.role = role
            it.enabled = enabled
        }
        return createUser(user)
    }
}
