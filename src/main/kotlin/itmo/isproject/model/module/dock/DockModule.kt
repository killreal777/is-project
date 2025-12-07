package itmo.isproject.model.module.dock

import itmo.isproject.model.module.Module
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "dock_module")
class DockModule(

    @OneToMany(mappedBy = "dockModule", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var dockingSpots: MutableList<DockingSpot> = ArrayList(),

    blueprint: DockModuleBlueprint? = null

) : Module<DockModuleBlueprint>(blueprint)
