package itmo.isproject.mapper.user

import itmo.isproject.dto.user.spaceship.CreateSpaceshipRequest
import itmo.isproject.dto.user.spaceship.SpaceshipDto
import itmo.isproject.mapper.EntityMapper
import itmo.isproject.model.user.Spaceship
import org.mapstruct.Mapper

@Mapper(uses = [UserMapper::class])
interface SpaceshipMapper : EntityMapper<SpaceshipDto, Spaceship> {

    fun toEntity(request: CreateSpaceshipRequest): Spaceship
}
