package itmo.isproject.storage.model

import itmo.isproject.shared.module.model.BuildCost
import itmo.isproject.shared.module.model.ModuleBlueprint
import jakarta.persistence.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "storage_module_blueprint")
class StorageModuleBlueprint(

    @NotNull
    @Min(1)
    @Column(name = "capacity", nullable = false, updatable = false)
    var capacity: Int? = null,

    buildCost: BuildCost? = null,
    name: String? = null

) : ModuleBlueprint(buildCost, name)
