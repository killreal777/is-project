package itmo.isproject.mapper.module.production

import itmo.isproject.dto.module.production.ProductionModuleBlueprintDto
import itmo.isproject.mapper.EntityMapper
import itmo.isproject.model.module.production.ProductionModuleBlueprint
import org.mapstruct.Mapper

@Mapper
interface ProductionModuleBlueprintMapper : EntityMapper<ProductionModuleBlueprintDto, ProductionModuleBlueprint>
