package itmo.isproject.model.module.production

import itmo.isproject.model.module.Module
import itmo.isproject.model.user.User
import jakarta.persistence.*

@Entity
@Table(name = "production_module")
class ProductionModule(

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    var state: ProductionModuleState = ProductionModuleState.OFF,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "engineer_id", referencedColumnName = "id", unique = true)
    var engineer: User? = null,

    blueprint: ProductionModuleBlueprint? = null

) : Module<ProductionModuleBlueprint>(blueprint)
