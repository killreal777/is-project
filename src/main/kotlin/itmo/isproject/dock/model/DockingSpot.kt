package itmo.isproject.dock.model

import itmo.isproject.shared.common.model.IntIdEntity
import itmo.isproject.shared.user.model.Spaceship
import jakarta.persistence.*

@Entity
@Table(name = "docking_spot")
class DockingSpot(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dock_module_id", referencedColumnName = "id", nullable = false, updatable = false)
    var dockModule: DockModule? = null,

    @Column(name = "size", length = 1, nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    var size: Spaceship.Size? = null,

    @Column(name = "is_occupied", nullable = false)
    var isOccupied: Boolean = false,

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "spaceship_id", referencedColumnName = "id", unique = true)
    var spaceship: Spaceship? = null

) : IntIdEntity()
