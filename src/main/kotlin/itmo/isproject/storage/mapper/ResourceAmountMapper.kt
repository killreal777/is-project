package itmo.isproject.storage.mapper

import itmo.isproject.shared.resource.dto.ResourceAmountDto
import itmo.isproject.shared.common.mapper.EntityMapper
import itmo.isproject.shared.resource.mapper.ResourceMapper
import itmo.isproject.shared.resource.model.ResourceAmount
import itmo.isproject.storage.model.StoredResource
import org.mapstruct.Mapper

@Mapper(uses = [ResourceMapper::class])
interface ResourceAmountMapper : EntityMapper<ResourceAmountDto, ResourceAmount> {

    fun toDto(storedResource: StoredResource): ResourceAmountDto
}