package itmo.isproject.repository.module.storage

import itmo.isproject.model.module.storage.StorageModuleBlueprint
import itmo.isproject.repository.module.ModuleBlueprintRepository
import org.springframework.stereotype.Repository

@Repository
interface StorageModuleBlueprintRepository : ModuleBlueprintRepository<StorageModuleBlueprint>
