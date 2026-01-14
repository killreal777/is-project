package itmo.isproject.shared.user.repository

import itmo.isproject.shared.user.model.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account, Int> {

    fun findByUserId(userId: Int?): Account?

    fun findByUserUsernameInternal(username: String?): Account?

    @Query("SELECT a FROM Account a WHERE a.user.role = 'ROLE_OWNER'")
    fun findOwnerAccount(): Account?
}
