package itmo.is.project.service.user;

import itmo.is.project.model.user.Account;
import itmo.is.project.model.user.User;
import itmo.is.project.repository.user.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Account createAccount(User user) {
        Account account = new Account();
        account.setUser(user);
        return accountRepository.save(account);
    }

    public Account getAccount(Integer userId) {
        return accountRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("User ID not found: " + userId));
    }

    public Account deposit(Integer userId, Integer amount) {
        Account account = getAccount(userId);
        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }

    public Account withdraw(Integer userId, Integer amount) {
        Account account = getAccount(userId);
        Integer funds = account.getBalance();
        if (funds < amount) {
            throw new IllegalStateException("Insufficient funds: " + funds);
        }
        account.setBalance(funds - amount);
        return accountRepository.save(account);
    }
}
