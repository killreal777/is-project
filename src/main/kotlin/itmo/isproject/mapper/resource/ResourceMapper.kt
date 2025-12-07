package itmo.isproject.mapper.resource

import itmo.isproject.dto.resource.ResourceDto
import itmo.isproject.mapper.EntityMapper
import itmo.isproject.model.resource.Resource
import org.mapstruct.Mapper

@Mapper
interface ResourceMapper : EntityMapper<ResourceDto, Resource>
