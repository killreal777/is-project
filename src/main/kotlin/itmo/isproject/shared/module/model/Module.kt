package itmo.isproject.shared.module.model

import itmo.isproject.shared.common.model.IntIdEntity
import jakarta.persistence.*

@MappedSuperclass
abstract class Module<Blueprint : ModuleBlueprint>(

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "blueprint_id", referencedColumnName = "id", nullable = false, updatable = false)
    var blueprint: Blueprint? = null

) : IntIdEntity()
