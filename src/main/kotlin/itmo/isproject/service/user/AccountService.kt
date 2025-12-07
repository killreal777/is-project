package itmo.isproject.service.user

import itmo.isproject.dto.user.AccountDto
import itmo.isproject.mapper.user.AccountMapper
import itmo.isproject.model.user.Account
import itmo.isproject.model.user.User
import itmo.isproject.repository.user.AccountRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val accountMapper: AccountMapper
) {

    @Transactional
    fun createAccount(user: User) {
        accountRepository.findByUserId(user.id)?.also { _ ->
            throw IllegalStateException("Account already exists")
        }
        val account = Account()
        account.user = user
        accountRepository.save(account)
    }

    fun findAllAccounts(pageable: Pageable): Page<AccountDto> {
        return accountRepository.findAll(pageable).map { accountMapper.toDto(it) }
    }

    fun findAccountByUserId(userId: Int): AccountDto {
        return accountMapper.toDto(findByUserId(userId))
    }

    @Transactional
    fun deposit(userId: Int, amount: Int): AccountDto {
        var account = findByUserId(userId)
        account.deposit(amount)
        account = accountRepository.save(account)
        return accountMapper.toDto(account)
    }

    @Transactional
    fun withdraw(userId: Int, amount: Int): AccountDto {
        var account = findByUserId(userId)
        account.withdraw(amount)
        if (account.balance < 0) {
            throw IllegalStateException("Insufficient funds")
        }
        account = accountRepository.save(account)
        return accountMapper.toDto(account)
    }

    @Transactional
    fun transferFundsBetweenStationAndUser(userId: Int?, stationBalanceChange: Int) {
        val user = findByUserId(userId)
        val station = accountRepository.findOwnerAccount()
            ?: throw EntityNotFoundException("Owner account not found")
        station.balance += stationBalanceChange
        user.balance -= stationBalanceChange
        if (station.balance < 0 || (user.balance) < 0) {
            throw IllegalStateException("Insufficient funds")
        }
        accountRepository.save(user)
        accountRepository.save(station)
    }

    private fun findByUserId(userId: Int?): Account {
        return accountRepository.findByUserId(userId)
            ?: throw EntityNotFoundException("Account not found with user id: $userId")
    }
}
