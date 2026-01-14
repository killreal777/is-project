package itmo.isproject.shared.module.repository

import itmo.isproject.shared.module.model.ModuleBlueprint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface ModuleBlueprintRepository<T : ModuleBlueprint> : JpaRepository<T, Int>
