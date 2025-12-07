package itmo.isproject.mapper.module.storage

import itmo.isproject.dto.module.storage.StorageModuleBlueprintDto
import itmo.isproject.mapper.EntityMapper
import itmo.isproject.model.module.storage.StorageModuleBlueprint
import org.mapstruct.Mapper

@Mapper
interface StorageModuleBlueprintMapper : EntityMapper<StorageModuleBlueprintDto, StorageModuleBlueprint>
