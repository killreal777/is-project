package itmo.is.project.repository.module.storage;

import itmo.is.project.model.module.storage.StorageModuleBlueprint;
import itmo.is.project.repository.module.ModuleBlueprintRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageModuleBlueprintRepository extends ModuleBlueprintRepository<StorageModuleBlueprint> {
}
