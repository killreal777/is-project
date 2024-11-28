package itmo.is.project.repository.module.storage;

import itmo.is.project.model.module.storage.StorageModule;
import itmo.is.project.model.module.storage.StorageModuleFreeSpace;
import itmo.is.project.repository.module.ModuleRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StorageModuleRepository extends ModuleRepository<StorageModule> {
//    @Query("""
//            SELECT sm, smb.capacity - SUM(sr.amount) FROM StorageModule sm
//            JOIN StorageModuleBlueprint smb ON sm.blueprint = smb
//            JOIN StoredResource sr ON sr.storage = sm
//            GROUP BY sm, smb.capacity
//            """)
//    List<Object[]> getId();

    @Query("SELECT get_total_free_space_in_storages()")
    Integer getTotalFreeSpaceInStorages();

    @Query("SELECT get_free_space_in_storage(:storageModuleId)")
    Integer getFreeSpaceInStorage(Integer storageModuleId);

    @Query("""
            SELECT new org.springframework.data.util.Pair(sm, get_free_space_in_storage(sm.id))
            FROM StorageModule sm ORDER BY get_free_space_in_storage(sm.id) DESC
            """)
    List<Pair<StorageModule, Integer>> findAllHavingFreeSpace();
}
