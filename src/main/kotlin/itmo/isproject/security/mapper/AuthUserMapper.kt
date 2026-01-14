package itmo.isproject.security.mapper

import itmo.isproject.shared.user.model.User
import itmo.isproject.security.dto.AuthenticationRequest
import itmo.isproject.security.dto.RegistrationRequest
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface AuthUserMapper {

    @Mapping(target = "passwordInternal", source = "password")
    @Mapping(target = "usernameInternal", source = "username")
    fun toEntity(registrationRequest: RegistrationRequest): User

    @Mapping(target = "usernameInternal", source = "username")
    @Mapping(target = "passwordInternal", source = "password")
    fun toEntity(authenticationRequest: AuthenticationRequest): User
}