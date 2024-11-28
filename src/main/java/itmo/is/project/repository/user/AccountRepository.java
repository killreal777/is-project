package itmo.is.project.repository.user;

import itmo.is.project.model.user.Account;
import itmo.is.project.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUserId(Integer userId);

    Optional<Account> findByUserUsername(String username);

    @Query("SELECT a FROM Account a WHERE a.user.role = 'ROLE_OWNER'")
    Optional<Account> findOwnerAccount();

    String user(User user);
}
