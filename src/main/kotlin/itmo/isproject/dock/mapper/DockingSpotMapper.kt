package itmo.isproject.dock.mapper

import itmo.isproject.dock.dto.DockingSpotDto
import itmo.isproject.shared.common.mapper.EntityMapper
import itmo.isproject.shared.user.mapper.SpaceshipMapper
import itmo.isproject.dock.model.DockingSpot
import org.mapstruct.Mapper

@Mapper(uses = [SpaceshipMapper::class])
interface DockingSpotMapper : EntityMapper<DockingSpotDto, DockingSpot>
