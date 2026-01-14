package itmo.isproject.shared.user.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.withLoggingContext
import itmo.isproject.shared.user.dto.AccountDto
import itmo.isproject.shared.user.mapper.AccountMapper
import itmo.isproject.shared.user.model.Account
import itmo.isproject.shared.user.model.User
import itmo.isproject.shared.user.repository.AccountRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val accountMapper: AccountMapper
) {

    @Transactional
    fun createAccount(user: User) {
        withLoggingContext("userId" to user.id.toString(), "username" to user.usernameInternal) {
            logger.info { "Creating account" }
        }
        accountRepository.findByUserId(user.id)?.also { _ ->
            withLoggingContext("userId" to user.id.toString()) {
                logger.warn { "Account already exists" }
            }
            throw IllegalStateException("Account already exists")
        }
        val account = Account()
        account.user = user
        accountRepository.save(account)
        withLoggingContext("userId" to user.id.toString()) {
            logger.info { "Account created" }
        }
    }

    fun findAllAccounts(pageable: Pageable): Page<AccountDto> {
        withLoggingContext("page" to pageable.pageNumber.toString()) {
            logger.debug { "Fetching all accounts" }
        }
        return accountRepository.findAll(pageable).map { accountMapper.toDto(it) }
    }

    fun findAccountByUserId(userId: Int): AccountDto {
        withLoggingContext("userId" to userId.toString()) {
            logger.debug { "Fetching account by userId" }
        }
        return accountMapper.toDto(findByUserId(userId))
    }

    @Transactional
    fun deposit(userId: Int, amount: Int): AccountDto {
        withLoggingContext("userId" to userId.toString(), "amount" to amount.toString()) {
            logger.info { "Depositing" }
        }
        var account = findByUserId(userId)
        account.deposit(amount)
        account = accountRepository.save(account)
        withLoggingContext("userId" to userId.toString(), "newBalance" to account.balance.toString()) {
            logger.info { "Deposit completed" }
        }
        return accountMapper.toDto(account)
    }

    @Transactional
    fun withdraw(userId: Int, amount: Int): AccountDto {
        withLoggingContext("userId" to userId.toString(), "amount" to amount.toString()) {
            logger.info { "Withdrawing" }
        }
        var account = findByUserId(userId)
        account.withdraw(amount)
        if (account.balance < 0) {
            withLoggingContext("userId" to userId.toString(), "balance" to account.balance.toString()) {
                logger.warn { "Insufficient funds" }
            }
            throw IllegalStateException("Insufficient funds")
        }
        account = accountRepository.save(account)
        withLoggingContext("userId" to userId.toString(), "newBalance" to account.balance.toString()) {
            logger.info { "Withdrawal completed" }
        }
        return accountMapper.toDto(account)
    }

    @Transactional
    fun transferFundsBetweenStationAndUser(userId: Int?, stationBalanceChange: Int) {
        withLoggingContext(
            "userId" to (userId?.toString() ?: "null"),
            "stationBalanceChange" to stationBalanceChange.toString()
        ) {
            logger.info { "Transferring funds" }
        }
        val user = findByUserId(userId)
        val station = accountRepository.findOwnerAccount()
            ?: throw EntityNotFoundException("Owner account not found")
        station.balance += stationBalanceChange
        user.balance -= stationBalanceChange
        if (station.balance < 0 || (user.balance) < 0) {
            withLoggingContext(
                "userId" to (userId?.toString() ?: "null"),
                "userBalance" to user.balance.toString(),
                "stationBalance" to station.balance.toString()
            ) {
                logger.warn { "Insufficient funds for transfer" }
            }
            throw IllegalStateException("Insufficient funds")
        }
        accountRepository.save(user)
        accountRepository.save(station)
        withLoggingContext(
            "userId" to (userId?.toString() ?: "null"),
            "userBalance" to user.balance.toString(),
            "stationBalance" to station.balance.toString()
        ) {
            logger.info { "Transfer completed" }
        }
    }

    private fun findByUserId(userId: Int?): Account {
        return accountRepository.findByUserId(userId)
            ?: throw EntityNotFoundException("Account not found with user id: $userId")
    }
}
