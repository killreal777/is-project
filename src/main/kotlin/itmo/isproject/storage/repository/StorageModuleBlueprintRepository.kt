package itmo.isproject.storage.repository

import itmo.isproject.storage.model.StorageModuleBlueprint
import itmo.isproject.shared.module.repository.ModuleBlueprintRepository
import org.springframework.stereotype.Repository

@Repository
interface StorageModuleBlueprintRepository : ModuleBlueprintRepository<StorageModuleBlueprint>
