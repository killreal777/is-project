package itmo.isproject.repository.module

import itmo.isproject.model.module.Module
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface ModuleRepository<T : Module<*>> : JpaRepository<T, Int>
