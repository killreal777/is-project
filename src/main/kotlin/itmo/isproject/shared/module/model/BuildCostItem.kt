package itmo.isproject.shared.module.model

import itmo.isproject.shared.common.model.EmbeddedIdEntity
import itmo.isproject.shared.resource.model.Resource
import itmo.isproject.shared.resource.model.ResourceAmountHolder
import jakarta.persistence.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.io.Serializable

@Entity
@Table(name = "build_cost_item")
class BuildCostItem(

    @MapsId("buildCostId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "build_cost_id", referencedColumnName = "id")
    var buildCost: BuildCost? = null,

    @MapsId("resourceId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resource_id", referencedColumnName = "id")
    override var resource: Resource? = null,

    @NotNull
    @Min(1)
    @Column(name = "amount", nullable = false, updatable = false)
    override var amount: Int? = null

) : EmbeddedIdEntity<BuildCostItemId>(), ResourceAmountHolder

@Embeddable
data class BuildCostItemId(
    val buildCostId: Int?,
    val resourceId: Int?
) : Serializable
