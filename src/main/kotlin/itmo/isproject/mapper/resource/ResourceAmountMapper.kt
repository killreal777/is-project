package itmo.isproject.mapper.resource

import itmo.isproject.dto.resource.ResourceAmountDto
import itmo.isproject.mapper.EntityMapper
import itmo.isproject.model.module.storage.StoredResource
import itmo.isproject.model.resource.ResourceAmount
import org.mapstruct.Mapper

@Mapper(uses = [ResourceMapper::class])
interface ResourceAmountMapper : EntityMapper<ResourceAmountDto, ResourceAmount> {

    fun toDto(storedResource: StoredResource): ResourceAmountDto
}
