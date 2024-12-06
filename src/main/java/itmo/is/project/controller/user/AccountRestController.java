package itmo.is.project.controller.user;

import itmo.is.project.dto.user.AccountDto;
import itmo.is.project.model.user.User;
import itmo.is.project.service.user.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/v1/balances")
@RequiredArgsConstructor
public class AccountRestController {
    private final AccountService accountService;

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping
    public ResponseEntity<Page<AccountDto>> getAllAccounts(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(accountService.findAllAccounts(pageable));
    }

    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_MANAGER')")
    @GetMapping("/{userId}")
    public ResponseEntity<AccountDto> getAccountByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(accountService.findAccountByUserId(userId));
    }

    @GetMapping("/my")
    public ResponseEntity<AccountDto> getMyAccount(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(accountService.findAccountByUserId(user.getId()));
    }

    @PutMapping("/my/deposit")
    public ResponseEntity<AccountDto> deposit(@RequestParam Integer amount, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(accountService.deposit(user.getId(), amount));
    }

    @PutMapping("/my/withdraw")
    public ResponseEntity<AccountDto> withdraw(@RequestParam Integer amount, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(accountService.withdraw(user.getId(), amount));
    }
}
