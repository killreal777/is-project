package itmo.isproject.dock.model

import itmo.isproject.shared.module.model.Module
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "dock_module")
class DockModule(

    @OneToMany(mappedBy = "dockModule", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var dockingSpots: MutableList<DockingSpot> = ArrayList(),

    blueprint: DockModuleBlueprint? = null

) : Module<DockModuleBlueprint>(blueprint)
