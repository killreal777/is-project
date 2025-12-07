package itmo.isproject.service.user

import itmo.isproject.dto.security.RegistrationRequest
import itmo.isproject.dto.user.UserDto
import itmo.isproject.mapper.user.UserMapper
import itmo.isproject.model.user.Role
import itmo.isproject.model.user.User
import itmo.isproject.repository.user.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val passwordEncoder: PasswordEncoder,
    private val accountService: AccountService
) {

    fun getAllUsers(pageable: Pageable, role: Role?): Page<UserDto> {
        return if (role == null) {
            userRepository.findAll(pageable).map { userMapper.toDto(it) }
        } else {
            userRepository.findAllByRole(role, pageable).map { userMapper.toDto(it) }
        }
    }

    fun getUserById(id: Int): UserDto {
        return userMapper.toDto(findUserById(id))
    }

    fun findUserById(userId: Int): User {
        return userRepository.findByIdOrNull(userId)
            ?: throw EntityNotFoundException("User not found with id: $userId")
    }

    fun findUserByUsername(username: String): User {
        return userRepository.findByUsernameInternal(username)
            ?: throw EntityNotFoundException("User not found with username: $username")
    }

    fun findAllDisabledUsers(pageable: Pageable): Page<User> {
        return userRepository.findAllByEnabledFalse(pageable)
    }

    fun isOwnerRegistered(): Boolean {
        return userRepository.existsByRole(Role.ROLE_OWNER)
    }

    fun updateUser(user: User) {
        userRepository.save(user)
    }

    fun deleteUser(user: User) {
        userRepository.delete(user)
    }

    fun createUser(request: RegistrationRequest, role: Role, enabled: Boolean): User {
        validateUsername(request.username)
        val user = userRepository.save(toUser(request, role, enabled))
        createAccountIfPilotOrOwner(user)
        return user
    }

    private fun validateUsername(username: String?) {
        if (userRepository.existsByUsernameInternal(username)) {
            throw IllegalArgumentException("Username is taken: $username")
        }
    }

    private fun toUser(request: RegistrationRequest, role: Role, enabled: Boolean): User {
        val user = userMapper.toEntity(request)
        user.passwordInternal = passwordEncoder.encode(user.passwordInternal)
        user.role = role
        user.enabled = enabled
        return user
    }

    private fun createAccountIfPilotOrOwner(user: User) {
        if (user.role == Role.ROLE_PILOT || user.role == Role.ROLE_OWNER) {
            accountService.createAccount(user)
        }
    }
}
