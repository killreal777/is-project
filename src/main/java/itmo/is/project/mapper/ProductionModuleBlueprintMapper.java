package itmo.is.project.mapper;

import itmo.is.project.dto.ProductionModuleBlueprintDto;
import itmo.is.project.model.module.production.ProductionModuleBlueprint;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductionModuleBlueprintMapper
        extends EntityMapper<ProductionModuleBlueprintDto, ProductionModuleBlueprint> {
}
