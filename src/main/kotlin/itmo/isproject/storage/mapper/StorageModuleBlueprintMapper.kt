package itmo.isproject.storage.mapper

import itmo.isproject.storage.dto.StorageModuleBlueprintDto
import itmo.isproject.shared.common.mapper.EntityMapper
import itmo.isproject.storage.model.StorageModuleBlueprint
import org.mapstruct.Mapper

@Mapper
interface StorageModuleBlueprintMapper : EntityMapper<StorageModuleBlueprintDto, StorageModuleBlueprint>
