package itmo.isproject.shared.user.mapper

import itmo.isproject.shared.user.dto.spaceship.CreateSpaceshipRequest
import itmo.isproject.shared.user.dto.spaceship.SpaceshipDto
import itmo.isproject.shared.common.mapper.EntityMapper
import itmo.isproject.shared.user.model.Spaceship
import org.mapstruct.Mapper

@Mapper(uses = [UserMapper::class])
interface SpaceshipMapper : EntityMapper<SpaceshipDto, Spaceship> {

    fun toEntity(request: CreateSpaceshipRequest): Spaceship
}
