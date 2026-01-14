package itmo.isproject.production.repository

import itmo.isproject.production.model.ProductionModule
import itmo.isproject.shared.module.repository.ModuleRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductionModuleRepository : ModuleRepository<ProductionModule>
