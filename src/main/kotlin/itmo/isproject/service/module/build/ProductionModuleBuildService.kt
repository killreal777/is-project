package itmo.isproject.service.module.build

import itmo.isproject.dto.module.production.ProductionModuleBlueprintDto
import itmo.isproject.dto.module.production.ProductionModuleDto
import itmo.isproject.mapper.module.production.ProductionModuleBlueprintMapper
import itmo.isproject.mapper.module.production.ProductionModuleMapper
import itmo.isproject.model.module.production.ProductionModule
import itmo.isproject.model.module.production.ProductionModuleBlueprint
import itmo.isproject.repository.module.production.ProductionModuleBlueprintRepository
import itmo.isproject.repository.module.production.ProductionModuleRepository
import itmo.isproject.service.module.StorageModuleService
import org.springframework.stereotype.Service

@Service
class ProductionModuleBuildService(
    storageModuleService: StorageModuleService,
    productionModuleBlueprintRepository: ProductionModuleBlueprintRepository,
    productionModuleBlueprintMapper: ProductionModuleBlueprintMapper,
    productionModuleRepository: ProductionModuleRepository,
    productionModuleMapper: ProductionModuleMapper
) : BuildService<ProductionModule, ProductionModuleBlueprint, ProductionModuleDto, ProductionModuleBlueprintDto>(
    storageModuleService,
    productionModuleBlueprintRepository,
    productionModuleBlueprintMapper,
    productionModuleRepository,
    productionModuleMapper,
    ::ProductionModule
)
