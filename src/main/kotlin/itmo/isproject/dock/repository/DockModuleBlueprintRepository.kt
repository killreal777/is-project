package itmo.isproject.dock.repository

import itmo.isproject.dock.model.DockModuleBlueprint
import itmo.isproject.shared.module.repository.ModuleBlueprintRepository
import org.springframework.stereotype.Repository

@Repository
interface DockModuleBlueprintRepository : ModuleBlueprintRepository<DockModuleBlueprint>
