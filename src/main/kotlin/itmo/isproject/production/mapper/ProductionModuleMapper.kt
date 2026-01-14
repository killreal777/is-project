package itmo.isproject.production.mapper

import itmo.isproject.production.dto.ProductionModuleDto
import itmo.isproject.shared.common.mapper.EntityMapper
import itmo.isproject.production.model.ProductionModule
import org.mapstruct.Mapper

@Mapper(uses = [ProductionModuleBlueprintMapper::class])
interface ProductionModuleMapper : EntityMapper<ProductionModuleDto, ProductionModule>
