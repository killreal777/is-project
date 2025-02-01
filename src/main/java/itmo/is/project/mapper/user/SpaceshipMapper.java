package itmo.is.project.mapper.user;

import itmo.is.project.dto.user.spaceship.CreateSpaceshipRequest;
import itmo.is.project.dto.user.spaceship.SpaceshipDto;
import itmo.is.project.mapper.EntityMapper;
import itmo.is.project.model.user.Spaceship;
import org.mapstruct.Mapper;

@Mapper(uses = {UserMapper.class})
public interface SpaceshipMapper extends EntityMapper<SpaceshipDto, Spaceship> {
    Spaceship toEntity(CreateSpaceshipRequest request);
}
