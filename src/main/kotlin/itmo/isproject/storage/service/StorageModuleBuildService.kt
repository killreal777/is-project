package itmo.isproject.storage.service

import itmo.isproject.shared.module.service.BuildService
import itmo.isproject.storage.dto.StorageModuleBlueprintDto
import itmo.isproject.storage.dto.StorageModuleDto
import itmo.isproject.storage.mapper.StorageModuleBlueprintMapper
import itmo.isproject.storage.mapper.StorageModuleMapper
import itmo.isproject.storage.model.StorageModule
import itmo.isproject.storage.model.StorageModuleBlueprint
import itmo.isproject.storage.repository.StorageModuleBlueprintRepository
import itmo.isproject.storage.repository.StorageModuleRepository
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
