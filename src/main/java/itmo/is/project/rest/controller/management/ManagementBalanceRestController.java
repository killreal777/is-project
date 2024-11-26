package itmo.is.project.rest.controller.management;

import itmo.is.project.dto.user.AccountDto;
import itmo.is.project.service.user.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/v1/management/balances")
@RequiredArgsConstructor
public class ManagementBalanceRestController {
    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<Page<AccountDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(accountService.findAllAccounts(pageable));
    }
}
