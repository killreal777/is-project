package itmo.is.project.controller.account;

import itmo.is.project.dto.user.AccountDto;
import itmo.is.project.dto.user.TransferRequest;
import itmo.is.project.model.user.User;
import itmo.is.project.service.user.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/v1/account/balance")
@RequiredArgsConstructor
public class AccountBalanceRestController {
    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<AccountDto> findByUserId(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(accountService.findAccountByUserId(user.getId()));
    }

    @PutMapping("/deposit")
    public ResponseEntity<AccountDto> deposit(
            @RequestBody TransferRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(accountService.deposit(user.getId(), request));
    }

    @PutMapping("/withdraw")
    public ResponseEntity<AccountDto> withdraw(
            @RequestBody TransferRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(accountService.withdraw(user.getId(), request));
    }
}
