package itmo.isproject.mapper.user

import itmo.isproject.dto.security.AuthenticationRequest
import itmo.isproject.dto.security.RegistrationRequest
import itmo.isproject.dto.user.UserDto
import itmo.isproject.mapper.EntityMapper
import itmo.isproject.model.user.User
import org.mapstruct.*

@Mapper
interface UserMapper : EntityMapper<UserDto, User> {

    @Mappings(
        Mapping(source = "usernameInternal", target = "username"),
    )
    override fun toDto(entity: User): UserDto

    @InheritInverseConfiguration
    @Mapping(target = "usernameInternal", source = "username")
    override fun toEntity(dto: UserDto): User

    @Mapping(target = "passwordInternal", source = "password")
    @Mapping(target = "usernameInternal", source = "username")
    fun toEntity(registrationRequest: RegistrationRequest): User

    @Mapping(target = "usernameInternal", source = "username")
    @Mapping(target = "passwordInternal", source = "password")
    fun toEntity(authenticationRequest: AuthenticationRequest): User
}