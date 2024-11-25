package itmo.is.project.mapper;

import itmo.is.project.dto.ResourceDto;
import itmo.is.project.model.resource.Resource;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ResourceMapper extends EntityMapper<ResourceDto, Resource> {
}
