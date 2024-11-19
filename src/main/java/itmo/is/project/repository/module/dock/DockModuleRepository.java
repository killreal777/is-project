package itmo.is.project.repository.module.dock;

import itmo.is.project.model.module.dock.DockModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DockModuleRepository extends JpaRepository<DockModule, Integer> {
}
