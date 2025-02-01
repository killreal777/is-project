package itmo.is.project.mapper.module.storage;

import itmo.is.project.dto.module.storage.StorageModuleBlueprintDto;
import itmo.is.project.mapper.EntityMapper;
import itmo.is.project.model.module.storage.StorageModuleBlueprint;
import org.mapstruct.Mapper;

@Mapper
public interface StorageModuleBlueprintMapper extends EntityMapper<StorageModuleBlueprintDto, StorageModuleBlueprint> {
}
