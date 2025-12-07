package itmo.isproject.service.module.build

import itmo.isproject.dto.module.storage.StorageModuleBlueprintDto
import itmo.isproject.dto.module.storage.StorageModuleDto
import itmo.isproject.mapper.module.storage.StorageModuleBlueprintMapper
import itmo.isproject.mapper.module.storage.StorageModuleMapper
import itmo.isproject.model.module.storage.StorageModule
import itmo.isproject.model.module.storage.StorageModuleBlueprint
import itmo.isproject.repository.module.storage.StorageModuleBlueprintRepository
import itmo.isproject.repository.module.storage.StorageModuleRepository
import itmo.isproject.service.module.StorageModuleService
import org.springframework.stereotype.Service

@Service
class StorageModuleBuildService(
    storageModuleService: StorageModuleService,
    storageModuleBlueprintRepository: StorageModuleBlueprintRepository,
    storageModuleBlueprintMapper: StorageModuleBlueprintMapper,
    storageModuleRepository: StorageModuleRepository,
    storageModuleMapper: StorageModuleMapper
) : BuildService<StorageModule, StorageModuleBlueprint, StorageModuleDto, StorageModuleBlueprintDto>(
    storageModuleService,
    storageModuleBlueprintRepository,
    storageModuleBlueprintMapper,
    storageModuleRepository,
    storageModuleMapper,
    ::StorageModule
)
