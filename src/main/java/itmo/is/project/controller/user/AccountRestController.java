package itmo.is.project.controller.user;

import itmo.is.project.dto.user.AccountDto;
import itmo.is.project.dto.user.TransferRequest;
import itmo.is.project.model.user.User;
import itmo.is.project.service.user.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/v1")
@RequiredArgsConstructor
public class AccountRestController {
    private final AccountService accountService;

    @GetMapping("/balances")
    public ResponseEntity<Page<AccountDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(accountService.findAllAccounts(pageable));
    }

    @GetMapping("/balance")
    public ResponseEntity<AccountDto> findByUserId(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(accountService.findAccountByUserId(user.getId()));
    }

    @PutMapping("/balance/deposit")
    public ResponseEntity<AccountDto> deposit(
            @RequestBody TransferRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(accountService.deposit(user.getId(), request));
    }

    @PutMapping("/balance/withdraw")
    public ResponseEntity<AccountDto> withdraw(
            @RequestBody TransferRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(accountService.withdraw(user.getId(), request));
    }
}
