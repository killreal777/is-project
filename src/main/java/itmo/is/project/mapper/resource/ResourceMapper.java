package itmo.is.project.mapper.resource;

import itmo.is.project.dto.resource.ResourceDto;
import itmo.is.project.mapper.EntityMapper;
import itmo.is.project.model.resource.Resource;
import org.mapstruct.Mapper;


@Mapper
public interface ResourceMapper extends EntityMapper<ResourceDto, Resource> {
}
