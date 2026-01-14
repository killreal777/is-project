package itmo.isproject.storage.model

import itmo.isproject.shared.module.model.Module
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "storage_module")
class StorageModule(

    @OneToMany(mappedBy = "storageModule", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var storedResources: MutableList<StoredResource> = ArrayList(),

    blueprint: StorageModuleBlueprint? = null

) : Module<StorageModuleBlueprint>(blueprint)
