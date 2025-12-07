package itmo.isproject.service.module.build

import itmo.isproject.dto.module.dock.DockModuleBlueprintDto
import itmo.isproject.dto.module.dock.DockModuleDto
import itmo.isproject.mapper.module.dock.DockModuleBlueprintMapper
import itmo.isproject.mapper.module.dock.DockModuleMapper
import itmo.isproject.model.module.dock.DockModule
import itmo.isproject.model.module.dock.DockModuleBlueprint
import itmo.isproject.repository.module.dock.DockModuleBlueprintRepository
import itmo.isproject.repository.module.dock.DockModuleRepository
import itmo.isproject.service.module.StorageModuleService
import org.springframework.stereotype.Service

@Service
class DockModuleBuildService(
    storageModuleService: StorageModuleService,
    dockModuleBlueprintRepository: DockModuleBlueprintRepository,
    dockModuleBlueprintMapper: DockModuleBlueprintMapper,
    dockModuleRepository: DockModuleRepository,
    dockModuleMapper: DockModuleMapper
) : BuildService<DockModule, DockModuleBlueprint, DockModuleDto, DockModuleBlueprintDto>(
    storageModuleService,
    dockModuleBlueprintRepository,
    dockModuleBlueprintMapper,
    dockModuleRepository,
    dockModuleMapper,
    ::DockModule
)
