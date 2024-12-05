package itmo.is.project.security.service;

import itmo.is.project.dto.security.AuthenticationRequest;
import itmo.is.project.dto.security.JwtResponse;
import itmo.is.project.dto.security.RegistrationRequest;
import itmo.is.project.dto.user.UserDto;
import itmo.is.project.mapper.user.UserMapper;
import itmo.is.project.model.user.Role;
import itmo.is.project.model.user.User;
import itmo.is.project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public JwtResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        User user = userService.findUserByUsername(request.username());
        validateUserEnabled(user);
        return new JwtResponse(jwtService.generateToken(user));
    }

    public JwtResponse registerOwner(RegistrationRequest request) {
        if (userService.isOwnerRegistered()) {
            throw new IllegalStateException("Owner is already registered");
        }
        boolean enabled = true;
        User user = userService.createUser(request, Role.ROLE_OWNER, enabled);
        return new JwtResponse(jwtService.generateToken(user));
    }

    public JwtResponse registerPilot(RegistrationRequest request) {
        boolean enabled = true;
        User user = userService.createUser(request, Role.ROLE_PILOT, enabled);
        return new JwtResponse(jwtService.generateToken(user));
    }

    public void applyManagerRegistrationRequest(RegistrationRequest request) {
        boolean enabled = false;
        userService.createUser(request, Role.ROLE_MANAGER, enabled);
    }

    public void applyEngineerRegistrationRequest(RegistrationRequest request) {
        boolean enabled = false;
        userService.createUser(request, Role.ROLE_ENGINEER, enabled);
    }

    public Page<UserDto> getPendingRegistrationRequests(Pageable pageable) {
        return userService.findAllDisabledUsers(pageable).map(userMapper::toDto);
    }

    public void approveRegistrationRequest(Integer userId) {
        User user = userService.findUserById(userId);
        user.setEnabled(true);
        userService.updateUser(user);
    }

    public void rejectAdminRegistrationRequest(Integer userId) {
        User user = userService.findUserById(userId);
        validateUserNotEnabled(user);
        userService.deleteUser(user);
    }

    private void validateUserEnabled(User user) {
        if (!user.isEnabled()) {
            throw new IllegalStateException("User is disabled: " + user.getUsername());
        }
    }

    private void validateUserNotEnabled(User user) {
        if (user.isEnabled()) {
            throw new IllegalStateException("User is enabled: " + user.getUsername());
        }
    }
}