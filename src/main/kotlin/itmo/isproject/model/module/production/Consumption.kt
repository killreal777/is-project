package itmo.isproject.model.module.production

import itmo.isproject.model.EmbeddedIdEntity
import itmo.isproject.model.resource.Resource
import itmo.isproject.model.resource.ResourceAmountHolder
import jakarta.persistence.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.io.Serializable

@Entity
@Table(name = "consumption")
class Consumption : EmbeddedIdEntity<ConsumptionId>(), ResourceAmountHolder {

    @MapsId("blueprintId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "blueprint_id", referencedColumnName = "id")
    var blueprint: ProductionModuleBlueprint? = null

    @MapsId("resourceId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resource_id", referencedColumnName = "id", nullable = false, updatable = false)
    override var resource: Resource? = null

    @NotNull
    @Min(1)
    @Column(name = "amount", nullable = false, updatable = false)
    override var amount: Int? = null
}

@Embeddable
data class ConsumptionId(
    val blueprintId: Int?,
    val resourceId: Int?
) : Serializable