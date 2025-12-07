package itmo.isproject.mapper.module.storage

import itmo.isproject.dto.module.storage.StoredResourceDto
import itmo.isproject.mapper.EntityMapper
import itmo.isproject.model.module.storage.StoredResource
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface StoredResourceMapper : EntityMapper<StoredResourceDto, StoredResource> {

    @Mapping(source = "storageModule.id", target = "storageModuleId")
    override fun toDto(entity: StoredResource): StoredResourceDto
}
