package itmo.is.project.repository.module.storage;

import itmo.is.project.model.module.storage.StorageModuleBlueprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageModuleBlueprintRepository extends JpaRepository<StorageModuleBlueprint, Integer> {
}
