package itmo.isproject.repository.module.dock

import itmo.isproject.model.module.dock.DockModule
import itmo.isproject.repository.module.ModuleRepository
import org.springframework.stereotype.Repository

@Repository
interface DockModuleRepository : ModuleRepository<DockModule>