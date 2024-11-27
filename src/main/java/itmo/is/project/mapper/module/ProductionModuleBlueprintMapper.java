package itmo.is.project.mapper.module;

import itmo.is.project.dto.module.production.ProductionModuleBlueprintDto;
import itmo.is.project.mapper.EntityMapper;
import itmo.is.project.model.module.production.ProductionModuleBlueprint;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductionModuleBlueprintMapper
        extends EntityMapper<ProductionModuleBlueprintDto, ProductionModuleBlueprint> {
}
