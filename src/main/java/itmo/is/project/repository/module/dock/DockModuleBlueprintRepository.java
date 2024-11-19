package itmo.is.project.repository.module.dock;

import itmo.is.project.model.module.dock.DockModuleBlueprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DockModuleBlueprintRepository extends JpaRepository<DockModuleBlueprint, Integer> {
}
