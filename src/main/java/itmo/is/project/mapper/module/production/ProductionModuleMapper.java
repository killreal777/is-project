package itmo.is.project.mapper.module.production;

import itmo.is.project.dto.module.production.ProductionModuleDto;
import itmo.is.project.mapper.EntityMapper;
import itmo.is.project.model.module.production.ProductionModule;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductionModuleBlueprintMapper.class})
public interface ProductionModuleMapper extends EntityMapper<ProductionModuleDto, ProductionModule> {

    @Override
    @Mapping(target = "productionModuleBlueprintDto", source = "blueprint")
    ProductionModuleDto toDto(ProductionModule entity);

    @Override
    @Mapping(target = "blueprint", source = "productionModuleBlueprintDto")
    ProductionModule toEntity(ProductionModuleDto dto);
}
