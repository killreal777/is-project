package itmo.isproject.dock.repository

import itmo.isproject.dock.model.DockModule
import itmo.isproject.shared.module.repository.ModuleRepository
import org.springframework.stereotype.Repository

@Repository
interface DockModuleRepository : ModuleRepository<DockModule>