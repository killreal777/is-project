package itmo.is.project.repository.module;

import itmo.is.project.model.module.ModuleBlueprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleBlueprintRepository extends JpaRepository<ModuleBlueprint, Integer> {
}
