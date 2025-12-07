package itmo.isproject.controller.user

import itmo.isproject.dto.user.AccountDto
import itmo.isproject.model.user.User
import itmo.isproject.service.user.AccountService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/balances")
class AccountRestController(
    private val accountService: AccountService
) {

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping
    fun getAllAccounts(@PageableDefault pageable: Pageable): ResponseEntity<Page<AccountDto>> {
        return ResponseEntity.ok(accountService.findAllAccounts(pageable))
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping("/{userId}")
    fun getAccountByUserId(@PathVariable userId: Int): ResponseEntity<AccountDto> {
        return ResponseEntity.ok(accountService.findAccountByUserId(userId))
    }

    @GetMapping("/my")
    fun getMyAccount(@AuthenticationPrincipal user: User): ResponseEntity<AccountDto> {
        return ResponseEntity.ok(accountService.findAccountByUserId(user.id!!))
    }

    @PutMapping("/my/deposit")
    fun deposit(@RequestParam amount: Int, @AuthenticationPrincipal user: User): ResponseEntity<AccountDto> {
        return ResponseEntity.ok(accountService.deposit(user.id!!, amount))
    }

    @PutMapping("/my/withdraw")
    fun withdraw(@RequestParam amount: Int, @AuthenticationPrincipal user: User): ResponseEntity<AccountDto> {
        return ResponseEntity.ok(accountService.withdraw(user.id!!, amount))
    }
}
