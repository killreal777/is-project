package itmo.isproject.storage.mapper

import itmo.isproject.storage.dto.StorageModuleDto
import itmo.isproject.shared.common.mapper.EntityMapper
import itmo.isproject.storage.model.StorageModule
import org.mapstruct.Mapper

@Mapper(uses = [StorageModuleBlueprintMapper::class, StoredResourceMapper::class])
interface StorageModuleMapper : EntityMapper<StorageModuleDto, StorageModule>
