package itmo.isproject.model.module.production

import itmo.isproject.model.module.BuildCost
import itmo.isproject.model.module.ModuleBlueprint
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
