package itmo.isproject.shared.user.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.withLoggingContext
import itmo.isproject.shared.user.model.User
import itmo.isproject.shared.user.dto.UserDto
import itmo.isproject.shared.user.mapper.UserMapper
import itmo.isproject.shared.user.model.Role
import itmo.isproject.shared.user.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val accountService: AccountService
) {

    fun getAllUsers(pageable: Pageable, role: Role?): Page<UserDto> {
        withLoggingContext("page" to pageable.pageNumber.toString(), "role" to (role?.name ?: "null")) {
            logger.debug { "Fetching all users" }
        }
        return if (role == null) {
            userRepository.findAll(pageable).map { userMapper.toDto(it) }
        } else {
            userRepository.findAllByRole(role, pageable).map { userMapper.toDto(it) }
        }
    }

    fun getUserById(id: Int): UserDto {
        withLoggingContext("userId" to id.toString()) {
            logger.debug { "Fetching user by ID" }
        }
        return userMapper.toDto(findUserById(id))
    }

    fun findUserById(userId: Int): User {
        return userRepository.findByIdOrNull(userId)
            ?: throw EntityNotFoundException("User not found with id: $userId")
    }

    fun findUserByUsername(username: String): User {
        withLoggingContext("username" to username) {
            logger.debug { "Finding user by username" }
        }
        return userRepository.findByUsernameInternal(username)
            ?: throw EntityNotFoundException("User not found with username: $username")
    }

    fun findAllDisabledUsers(pageable: Pageable): Page<User> {
        withLoggingContext("page" to pageable.pageNumber.toString()) {
            logger.debug { "Fetching all disabled users" }
        }
        return userRepository.findAllByEnabledFalse(pageable)
    }

    fun isOwnerRegistered(): Boolean {
        return userRepository.existsByRole(Role.ROLE_OWNER)
    }

    fun updateUser(user: User) {
        withLoggingContext("userId" to user.id.toString(), "username" to user.usernameInternal) {
            logger.info { "Updating user" }
        }
        userRepository.save(user)
    }

    fun deleteUser(user: User) {
        withLoggingContext("userId" to user.id.toString(), "username" to user.usernameInternal) {
            logger.info { "Deleting user" }
        }
        userRepository.delete(user)
    }

    fun createUser(user: User): User {
        with(user) {
            withLoggingContext("username" to username, "role" to role?.name, "enabled" to enabled.toString()) {
                logger.info { "Creating user" }
            }
            validateUsername(username)
            val savedUser = userRepository.save(this)
            createAccountIfPilotOrOwner(savedUser)
            withLoggingContext("userId" to savedUser.id.toString()) {
                logger.info { "User created" }
            }
            return user
        }
    }

    private fun validateUsername(username: String?) {
        if (userRepository.existsByUsernameInternal(username)) {
            withLoggingContext("username" to (username ?: "null")) {
                logger.warn { "Username is taken" }
            }
            throw IllegalArgumentException("Username is taken: $username")
        }
    }

    private fun createAccountIfPilotOrOwner(user: User) {
        if (user.role == Role.ROLE_PILOT || user.role == Role.ROLE_OWNER) {
            withLoggingContext("role" to (user.role?.name ?: "null"), "userId" to user.id.toString()) {
                logger.debug { "Creating account for user" }
            }
            accountService.createAccount(user)
        }
    }
}
