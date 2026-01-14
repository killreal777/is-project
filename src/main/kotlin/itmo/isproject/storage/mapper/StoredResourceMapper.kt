package itmo.isproject.storage.mapper

import itmo.isproject.storage.dto.StoredResourceDto
import itmo.isproject.shared.common.mapper.EntityMapper
import itmo.isproject.storage.model.StoredResource
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface StoredResourceMapper : EntityMapper<StoredResourceDto, StoredResource> {

    @Mapping(source = "storageModule.id", target = "storageModuleId")
    override fun toDto(entity: StoredResource): StoredResourceDto
}
