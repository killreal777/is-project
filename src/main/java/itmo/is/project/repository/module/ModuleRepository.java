package itmo.is.project.repository.module;

import itmo.is.project.model.module.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ModuleRepository<T extends Module<?>> extends JpaRepository<T, Integer> {
}
