package itmo.isproject.mapper.module.dock

import itmo.isproject.dto.module.dock.DockingSpotDto
import itmo.isproject.mapper.EntityMapper
import itmo.isproject.mapper.user.SpaceshipMapper
import itmo.isproject.model.module.dock.DockingSpot
import org.mapstruct.Mapper

@Mapper(uses = [SpaceshipMapper::class])
interface DockingSpotMapper : EntityMapper<DockingSpotDto, DockingSpot>
