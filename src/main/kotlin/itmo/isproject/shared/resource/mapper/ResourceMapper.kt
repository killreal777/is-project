package itmo.isproject.shared.resource.mapper

import itmo.isproject.shared.common.mapper.EntityMapper
import itmo.isproject.shared.resource.dto.ResourceDto
import itmo.isproject.shared.resource.model.Resource
import org.mapstruct.Mapper

@Mapper
interface ResourceMapper : EntityMapper<ResourceDto, Resource>