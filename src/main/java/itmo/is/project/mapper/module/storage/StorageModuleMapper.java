package itmo.is.project.mapper.module.storage;

import itmo.is.project.dto.module.storage.StorageModuleDto;
import itmo.is.project.mapper.EntityMapper;
import itmo.is.project.model.module.storage.StorageModule;
import org.mapstruct.Mapper;

@Mapper(uses = {StorageModuleBlueprintMapper.class, StoredResourceMapper.class})
public interface StorageModuleMapper extends EntityMapper<StorageModuleDto, StorageModule> {
}
