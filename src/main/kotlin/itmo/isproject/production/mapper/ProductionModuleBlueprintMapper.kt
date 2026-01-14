package itmo.isproject.production.mapper

import itmo.isproject.production.dto.ProductionModuleBlueprintDto
import itmo.isproject.shared.common.mapper.EntityMapper
import itmo.isproject.production.model.ProductionModuleBlueprint
import org.mapstruct.Mapper

@Mapper
interface ProductionModuleBlueprintMapper : EntityMapper<ProductionModuleBlueprintDto, ProductionModuleBlueprint>
