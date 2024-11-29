package itmo.is.project.mapper.module.dock;

import itmo.is.project.dto.module.dock.DockingSpotDto;
import itmo.is.project.mapper.EntityMapper;
import itmo.is.project.mapper.user.SpaceshipMapper;
import itmo.is.project.model.module.dock.DockingSpot;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {SpaceshipMapper.class})
public interface DockingSpotMapper extends EntityMapper<DockingSpotDto, DockingSpot> {
}
