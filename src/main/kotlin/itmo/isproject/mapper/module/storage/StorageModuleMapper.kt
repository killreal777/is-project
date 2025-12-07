package itmo.isproject.mapper.module.storage

import itmo.isproject.dto.module.storage.StorageModuleDto
import itmo.isproject.mapper.EntityMapper
import itmo.isproject.model.module.storage.StorageModule
import org.mapstruct.Mapper

@Mapper(uses = [StorageModuleBlueprintMapper::class, StoredResourceMapper::class])
interface StorageModuleMapper : EntityMapper<StorageModuleDto, StorageModule>
