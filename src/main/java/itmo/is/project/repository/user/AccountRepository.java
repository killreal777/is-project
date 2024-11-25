package itmo.is.project.repository.user;

import itmo.is.project.model.user.Account;
import itmo.is.project.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUserId(Integer userId);

    @Query("select a from Account a join User u on a.userId = u.id where u.username = :username")
    Optional<Account> findByUsername(@Param("username") String username);

    String user(User user);
}
