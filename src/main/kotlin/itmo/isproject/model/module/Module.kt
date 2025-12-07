package itmo.isproject.model.module

import itmo.isproject.model.IntIdEntity
import jakarta.persistence.*

@MappedSuperclass
abstract class Module<Blueprint : ModuleBlueprint>(

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "blueprint_id", referencedColumnName = "id", nullable = false, updatable = false)
    var blueprint: Blueprint? = null

) : IntIdEntity()
