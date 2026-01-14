package itmo.isproject.dock.service

import itmo.isproject.shared.module.service.BuildService
import itmo.isproject.dock.dto.DockModuleBlueprintDto
import itmo.isproject.dock.dto.DockModuleDto
import itmo.isproject.dock.mapper.DockModuleBlueprintMapper
import itmo.isproject.dock.mapper.DockModuleMapper
import itmo.isproject.dock.model.DockModule
import itmo.isproject.dock.model.DockModuleBlueprint
import itmo.isproject.dock.repository.DockModuleBlueprintRepository
import itmo.isproject.dock.repository.DockModuleRepository
import itmo.isproject.storage.api.StorageService
import org.springframework.stereotype.Service

@Service
class DockModuleBuildService(
    storageService: StorageService,
    dockModuleBlueprintRepository: DockModuleBlueprintRepository,
    dockModuleBlueprintMapper: DockModuleBlueprintMapper,
    dockModuleRepository: DockModuleRepository,
    dockModuleMapper: DockModuleMapper
) : BuildService<DockModule, DockModuleBlueprint, DockModuleDto, DockModuleBlueprintDto>(
    storageService,
    dockModuleBlueprintRepository,
    dockModuleBlueprintMapper,
    dockModuleRepository,
    dockModuleMapper,
    ::DockModule
)