package itmo.isproject.repository.module

import itmo.isproject.model.module.ModuleBlueprint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface ModuleBlueprintRepository<T : ModuleBlueprint> : JpaRepository<T, Int>
