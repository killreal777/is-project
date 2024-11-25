package itmo.is.project.security.service;

import itmo.is.project.dto.security.AuthenticationRequest;
import itmo.is.project.dto.security.JwtResponse;
import itmo.is.project.dto.security.RegistrationRequest;
import itmo.is.project.dto.security.UserDto;
import itmo.is.project.mapper.user.UserMapper;
import itmo.is.project.model.user.Role;
import itmo.is.project.model.user.User;
import itmo.is.project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public JwtResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        User user = findUserByUsername(request.username());
        validateUserEnabled(user);
        return new JwtResponse(jwtService.generateToken(user));
    }

    public JwtResponse registerOwner(RegistrationRequest request) {
        if (userRepository.existsByRole(Role.ROLE_OWNER)) {
            throw new AuthenticationServiceException("Owner is already registered");
        }
        return createEnabledUser(request, Role.ROLE_OWNER);
    }

    public JwtResponse registerPilot(RegistrationRequest request) {
        return createEnabledUser(request, Role.ROLE_PILOT);
    }

    public void applyManagerRegistrationRequest(RegistrationRequest request) {
        createDisabledUser(request, Role.ROLE_MANAGER);
    }

    public void applyEngineerRegistrationRequest(RegistrationRequest request) {
        createDisabledUser(request, Role.ROLE_ENGINEER);
    }

    public Page<UserDto> getPendingRegistrationRequests(Pageable pageable) {
        return userRepository.findAllByEnabledFalse(pageable).map(userMapper::toDto);
    }

    public void approveRegistrationRequest(Integer userId) {
        User user = findUserById(userId);
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void rejectAdminRegistrationRequest(Integer userId) {
        User user = findUserById(userId);
        validateUserNotEnabled(user);
        userRepository.delete(user);
    }

    private void createDisabledUser(RegistrationRequest request, Role role) {
        boolean enabled = false;
        createUser(request, role, enabled);
    }

    private JwtResponse createEnabledUser(RegistrationRequest request, Role role) {
        boolean enabled = true;
        User user = createUser(request, role, enabled);
        return new JwtResponse(jwtService.generateToken(user));
    }

    private User createUser(RegistrationRequest request, Role role, boolean enabled) {
        validateRegisterRequest(request);
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(role);
        user.setEnabled(enabled);
        return userRepository.save(user);
    }

    private User findUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new AuthenticationServiceException("User not found with id: " + userId));
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    private void validateUserEnabled(User user) {
        if (!user.isEnabled()) {
            throw new AuthenticationServiceException("User is disabled: " + user.getUsername());
        }
    }

    private void validateUserNotEnabled(User user) {
        if (user.isEnabled()) {
            throw new AuthenticationServiceException("Cannot delete an enabled user");
        }
    }

    private void validateRegisterRequest(RegistrationRequest request) {
        validateUsername(request.username());
    }

    private void validateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new AuthenticationServiceException("Username " + username + " is taken");
        }
    }
}