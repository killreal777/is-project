package itmo.isproject.production.model

import itmo.isproject.shared.module.model.BuildCost
import itmo.isproject.shared.module.model.ModuleBlueprint
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "production_module_blueprint")
class ProductionModuleBlueprint(

    @OneToOne(mappedBy = "blueprint", cascade = [CascadeType.ALL])
    var production: Production? = null,

    @OneToMany(mappedBy = "blueprint", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var consumption: MutableList<Consumption> = ArrayList(),

    buildCost: BuildCost? = null,
    name: String? = null,

) : ModuleBlueprint(buildCost, name)
