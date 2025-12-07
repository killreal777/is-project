package itmo.isproject.repository.module.dock

import itmo.isproject.model.module.dock.DockModuleBlueprint
import itmo.isproject.repository.module.ModuleBlueprintRepository
import org.springframework.stereotype.Repository

@Repository
interface DockModuleBlueprintRepository : ModuleBlueprintRepository<DockModuleBlueprint>
