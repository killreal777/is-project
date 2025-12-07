package itmo.isproject.mapper.module.production

import itmo.isproject.dto.module.production.ProductionModuleDto
import itmo.isproject.mapper.EntityMapper
import itmo.isproject.model.module.production.ProductionModule
import org.mapstruct.Mapper

@Mapper(uses = [ProductionModuleBlueprintMapper::class])
interface ProductionModuleMapper : EntityMapper<ProductionModuleDto, ProductionModule>
