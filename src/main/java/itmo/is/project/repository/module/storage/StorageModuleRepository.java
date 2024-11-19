package itmo.is.project.repository.module.storage;

import itmo.is.project.model.module.storage.StorageModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageModuleRepository extends JpaRepository<StorageModule, Integer> {
}
