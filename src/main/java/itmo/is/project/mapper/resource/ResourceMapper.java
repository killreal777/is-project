package itmo.is.project.mapper.resource;

import itmo.is.project.dto.ResourceDto;
import itmo.is.project.mapper.EntityMapper;
import itmo.is.project.model.resource.Resource;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ResourceMapper extends EntityMapper<ResourceDto, Resource> {
}
