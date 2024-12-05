package itmo.is.project.repository.module.storage;

import itmo.is.project.model.module.storage.StorageModule;
import itmo.is.project.model.module.storage.StorageModuleFreeSpace;
import itmo.is.project.repository.module.ModuleRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface StorageModuleRepository extends ModuleRepository<StorageModule> {

    @Query("SELECT get_total_free_space_in_storages()")
    Integer getTotalFreeSpaceInStorages();

    @Query("""
            SELECT sm, get_free_space_in_storage(sm.id) FROM StorageModule sm
            ORDER BY get_free_space_in_storage(sm.id) DESC
            """)
    Object[][] findAllHavingFreeSpaceRaw();

    default List<StorageModuleFreeSpace> findAllHavingFreeSpace() {;
        return Arrays.stream(findAllHavingFreeSpaceRaw())
                .map(array -> {
                    StorageModule storageModule = (StorageModule) array[0];
                    int freeSpace = (int) array[1];
                    return new StorageModuleFreeSpace(storageModule, freeSpace);
                })
                .toList();
    }
}
