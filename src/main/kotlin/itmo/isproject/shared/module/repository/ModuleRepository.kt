package itmo.isproject.shared.module.repository

import itmo.isproject.shared.module.model.Module
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface ModuleRepository<T : Module<*>> : JpaRepository<T, Int>
