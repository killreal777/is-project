package itmo.is.project.mapper.module.storage;

import itmo.is.project.dto.module.storage.StoredResourceDto;
import itmo.is.project.mapper.EntityMapper;
import itmo.is.project.model.module.storage.StoredResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface StoredResourceMapper extends EntityMapper<StoredResourceDto, StoredResource> {

    @Override
    @Mapping(source = "storageModule.id", target = "storageModuleId")
    StoredResourceDto toDto(StoredResource entity);
}
