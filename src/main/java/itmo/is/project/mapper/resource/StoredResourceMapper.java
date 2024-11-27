package itmo.is.project.mapper.resource;

import itmo.is.project.dto.resource.StoredResourceDto;
import itmo.is.project.mapper.EntityMapper;
import itmo.is.project.model.module.storage.StoredResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StoredResourceMapper extends EntityMapper<StoredResourceDto, StoredResource> {

    @Override
    @Mapping(source = "id.storageModuleId", target = "storageModuleId")
    StoredResourceDto toDto(StoredResource entity);
}
