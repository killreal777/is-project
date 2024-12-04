package itmo.is.project.service.user;

import itmo.is.project.dto.user.AccountDto;
import itmo.is.project.mapper.user.AccountMapper;
import itmo.is.project.model.user.Account;
import itmo.is.project.model.user.User;
import itmo.is.project.repository.user.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Transactional
    public void createAccount(User user) {
        accountRepository.findByUserId(user.getId()).ifPresent(account -> {
            throw new IllegalStateException("Account already exists");
        });
        Account account = new Account();
        account.setUser(user);
        accountRepository.save(account);
    }

    public Page<AccountDto> findAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable).map(accountMapper::toDto);
    }

    public AccountDto findAccountByUserId(Integer userId) {
        return accountMapper.toDto(findByUserId(userId));
    }

    @Transactional
    public AccountDto deposit(Integer userId, Integer amount) {
        Account account = findByUserId(userId);
        account.deposit(amount);
        account = accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    @Transactional
    public AccountDto withdraw(Integer userId, Integer amount) {
        Account account = findByUserId(userId);
        account.withdraw(amount);
        if (account.getBalance() < 0) {
            throw new IllegalStateException("Insufficient funds");
        }
        account = accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    @Transactional
    public void transferFundsBetweenStationAndUser(Integer userId, int stationBalanceChange) {
        Account user = findByUserId(userId);
        Account station = accountRepository.findOwnerAccount()
                .orElseThrow(() -> new EntityNotFoundException("Owner account not found"));
        station.setBalance(station.getBalance() + stationBalanceChange);
        user.setBalance(user.getBalance() - stationBalanceChange);
        if (station.getBalance() < 0 || user.getBalance() < 0) {
            throw new IllegalStateException("Insufficient funds");
        }
        accountRepository.save(user);
        accountRepository.save(station);
    }

    private Account findByUserId(Integer userId) {
        return accountRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with user id: " + userId));
    }
}
