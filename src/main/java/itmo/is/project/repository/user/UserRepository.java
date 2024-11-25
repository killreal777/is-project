package itmo.is.project.repository.user;

import itmo.is.project.model.user.Role;
import itmo.is.project.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUsername(String username);

    boolean existsByRole(Role role);

    Optional<User> findByUsername(String username);

    Page<User> findAllByEnabledFalse(Pageable pageable);
}
