package itmo.isproject.repository.user

import itmo.isproject.model.user.Role
import itmo.isproject.model.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Int> {
    
    fun existsByUsernameInternal(username: String?): Boolean

    fun existsByRole(role: Role?): Boolean

    fun findByUsernameInternal(username: String?): User?

    fun findAllByEnabledFalse(pageable: Pageable): Page<User>

    fun findAllByRole(role: Role?, pageable: Pageable): Page<User>
}
