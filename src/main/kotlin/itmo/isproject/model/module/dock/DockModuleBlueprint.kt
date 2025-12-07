package itmo.isproject.model.module.dock

import itmo.isproject.model.module.BuildCost
import itmo.isproject.model.module.ModuleBlueprint
import jakarta.persistence.*
import jakarta.validation.constraints.*

@Entity
@Table(name = "dock_module_blueprint")
class DockModuleBlueprint(

    @NotNull
    @Min(0)
    @Column(name = "s_docks_quantity", nullable = false, updatable = false)
    var smallDocksQuantity: Int? = null, // full word 'small' for correct MapStruct mapping

    @NotNull
    @Min(0)
    @Column(name = "m_docks_quantity", nullable = false, updatable = false)
    var mediumDocksQuantity: Int? = null, // full word 'medium' for correct MapStruct mapping

    @NotNull
    @Min(0)
    @Column(name = "l_docks_quantity", nullable = false, updatable = false)
    var largeDocksQuantity: Int? = null, // full word 'large' for correct MapStruct mapping

    buildCost: BuildCost? = null,
    name: String? = null,

) : ModuleBlueprint(buildCost, name) {

    @AssertTrue
    fun containsAtLeastOneDock(): Boolean {
        return (smallDocksQuantity ?: 0) + (mediumDocksQuantity ?: 0) + (largeDocksQuantity ?: 0) > 0
    }
}
