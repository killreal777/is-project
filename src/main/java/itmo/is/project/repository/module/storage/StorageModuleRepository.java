package itmo.is.project.repository.module.storage;

import itmo.is.project.model.module.storage.StorageModule;
import itmo.is.project.repository.module.ModuleRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageModuleRepository extends ModuleRepository<StorageModule> {
}
