package itmo.isproject.storage.repository

import itmo.isproject.storage.model.StorageModuleFreeSpace
import itmo.isproject.storage.model.StorageModule
import itmo.isproject.shared.module.repository.ModuleRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface StorageModuleRepository : ModuleRepository<StorageModule> {

    @Query("SELECT get_total_free_space_in_storages()")
    fun getTotalFreeSpaceInStorages(): Int?

    @Query("""
            SELECT sm, get_free_space_in_storage(sm.id) FROM StorageModule sm
            ORDER BY get_free_space_in_storage(sm.id) DESC
            """)
    fun findAllHavingFreeSpaceRaw(): Array<Array<Any>>

    fun findAllHavingFreeSpace(): List<StorageModuleFreeSpace> {
        return findAllHavingFreeSpaceRaw().map { array ->
            val storageModule = array[0] as StorageModule
            val freeSpace = array[1] as Int
            StorageModuleFreeSpace(storageModule to freeSpace)
        }
    }
}
