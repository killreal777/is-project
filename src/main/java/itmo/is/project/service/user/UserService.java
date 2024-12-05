package itmo.is.project.service.user;

import itmo.is.project.dto.security.RegistrationRequest;
import itmo.is.project.mapper.user.UserMapper;
import itmo.is.project.model.user.Role;
import itmo.is.project.model.user.User;
import itmo.is.project.repository.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;

    public User findUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
    }

    public Page<User> findAllDisabledUsers(Pageable pageable) {
        return userRepository.findAllByEnabledFalse(pageable);
    }

    public boolean isOwnerRegistered() {
        return userRepository.existsByRole(Role.ROLE_OWNER);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public User createUser(RegistrationRequest request, Role role, boolean enabled) {
        validateUsername(request.username());
        User user = userRepository.save(toUser(request, role, enabled));
        createAccountIfPilotOrOwner(user);
        return user;
    }

    private void validateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username is taken: " + username);
        }
    }

    private User toUser(RegistrationRequest request, Role role, boolean enabled) {
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(role);
        user.setEnabled(enabled);
        return user;
    }

    private void createAccountIfPilotOrOwner(User user) {
        if (user.getRole() == Role.ROLE_PILOT || user.getRole() == Role.ROLE_OWNER) {
            accountService.createAccount(user);
        }
    }
}
