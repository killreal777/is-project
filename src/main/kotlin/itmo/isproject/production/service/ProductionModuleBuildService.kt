package itmo.isproject.production.service

import itmo.isproject.shared.module.service.BuildService
import itmo.isproject.production.dto.ProductionModuleBlueprintDto
import itmo.isproject.production.dto.ProductionModuleDto
import itmo.isproject.production.mapper.ProductionModuleBlueprintMapper
import itmo.isproject.production.mapper.ProductionModuleMapper
import itmo.isproject.production.model.ProductionModule
import itmo.isproject.production.model.ProductionModuleBlueprint
import itmo.isproject.production.repository.ProductionModuleBlueprintRepository
import itmo.isproject.production.repository.ProductionModuleRepository
import itmo.isproject.storage.api.StorageService
import org.springframework.stereotype.Service

@Service
class ProductionModuleBuildService(
    storageService: StorageService,
    productionModuleBlueprintRepository: ProductionModuleBlueprintRepository,
    productionModuleBlueprintMapper: ProductionModuleBlueprintMapper,
    productionModuleRepository: ProductionModuleRepository,
    productionModuleMapper: ProductionModuleMapper
) : BuildService<ProductionModule, ProductionModuleBlueprint, ProductionModuleDto, ProductionModuleBlueprintDto>(
    storageService,
    productionModuleBlueprintRepository,
    productionModuleBlueprintMapper,
    productionModuleRepository,
    productionModuleMapper,
    ::ProductionModule
)