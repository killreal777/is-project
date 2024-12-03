package itmo.is.project.service.user;

import itmo.is.project.dto.user.AccountDto;
import itmo.is.project.dto.user.TransferRequest;
import itmo.is.project.mapper.user.AccountMapper;
import itmo.is.project.model.user.Account;
import itmo.is.project.model.user.User;
import itmo.is.project.repository.user.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public void createAccount(User user) {
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

    private Account findByUserId(Integer userId) {
        return accountRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("User ID not found: " + userId));
    }

    public AccountDto deposit(Integer userId, TransferRequest request) {
        Account account = findByUserId(userId);
        account.setBalance(account.getBalance() + request.amount());
        account = accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    public AccountDto withdraw(Integer userId, TransferRequest request) {
        Account account = findByUserId(userId);
        Integer funds = account.getBalance();
        if (funds < request.amount()) {
            throw new IllegalStateException("Insufficient funds: " + funds);
        }
        account.setBalance(funds - request.amount());
        account = accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    public void transferFundsBetweenStationAndUser(Integer userId, int stationBalanceChange) {
        Account user = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("User ID not found: " + userId));
        Account station = accountRepository.findOwnerAccount()
                .orElseThrow(() -> new IllegalStateException("Owner account not found"));
        station.setBalance(station.getBalance() + stationBalanceChange);
        user.setBalance(user.getBalance() - stationBalanceChange);
        if (station.getBalance() < 0 || user.getBalance() < 0) {
            throw new IllegalStateException("Insufficient funds");
        }
        accountRepository.save(user);
        accountRepository.save(station);
    }
}
