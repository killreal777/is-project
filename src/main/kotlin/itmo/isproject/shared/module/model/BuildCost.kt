package itmo.isproject.shared.module.model

import itmo.isproject.shared.common.model.IntIdEntity
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "build_cost")
class BuildCost(

    @OneToMany(mappedBy = "buildCost", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var items: MutableList<BuildCostItem> = ArrayList()

) : IntIdEntity()
