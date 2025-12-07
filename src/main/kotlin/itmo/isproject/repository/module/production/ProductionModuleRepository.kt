package itmo.isproject.repository.module.production

import itmo.isproject.model.module.production.ProductionModule
import itmo.isproject.repository.module.ModuleRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductionModuleRepository : ModuleRepository<ProductionModule>
