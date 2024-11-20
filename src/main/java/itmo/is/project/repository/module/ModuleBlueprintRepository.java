package itmo.is.project.repository.module;

import itmo.is.project.model.module.ModuleBlueprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ModuleBlueprintRepository<T extends ModuleBlueprint> extends JpaRepository<T, Integer> {
}
