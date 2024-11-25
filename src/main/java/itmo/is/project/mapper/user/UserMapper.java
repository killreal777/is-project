package itmo.is.project.mapper.user;

import itmo.is.project.dto.security.AuthenticationRequest;
import itmo.is.project.dto.security.RegistrationRequest;
import itmo.is.project.dto.user.UserDto;
import itmo.is.project.mapper.EntityMapper;
import itmo.is.project.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, User> {
    User toEntity(RegistrationRequest registrationRequest);

    User toEntity(AuthenticationRequest authenticationRequest);
}
