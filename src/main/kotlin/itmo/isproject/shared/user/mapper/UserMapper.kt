package itmo.isproject.shared.user.mapper

import itmo.isproject.shared.user.dto.UserDto
import itmo.isproject.shared.common.mapper.EntityMapper
import itmo.isproject.shared.user.model.User
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
}