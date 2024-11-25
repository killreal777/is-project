package itmo.is.project.rest.controller;

import itmo.is.project.dto.user.AccountDto;
import itmo.is.project.dto.user.TransferRequest;
import itmo.is.project.service.user.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/v1/users")
@RequiredArgsConstructor
public class AccountRestController {
    private final AccountService accountService;

    @GetMapping("/balances")
    public ResponseEntity<Page<AccountDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(accountService.findAllAccounts(pageable));
    }

    @GetMapping("/{username}/balance")
    public ResponseEntity<AccountDto> findByUserId(@PathVariable String username) {
        return ResponseEntity.ok(accountService.findAccountByUsername(username));
    }

    @PutMapping("/{username}/balance/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable String username, @RequestBody TransferRequest request) {
        return ResponseEntity.ok(accountService.deposit(username, request));
    }

    @PutMapping("/{username}/balance/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable String username, @RequestBody TransferRequest request) {
        return ResponseEntity.ok(accountService.withdraw(username, request));
    }
}
