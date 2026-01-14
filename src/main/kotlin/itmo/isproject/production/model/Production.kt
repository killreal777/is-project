package itmo.isproject.production.model

import itmo.isproject.shared.common.model.EmbeddedIdEntity
import itmo.isproject.shared.resource.model.Resource
import itmo.isproject.shared.resource.model.ResourceAmountHolder
import jakarta.persistence.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.io.Serializable

@Entity
@Table(name = "production")
class Production(
    
    @MapsId("blueprintId")
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "blueprint_id", referencedColumnName = "id")
    var blueprint: ProductionModuleBlueprint? = null,

    @MapsId("resourceId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resource_id", referencedColumnName = "id", nullable = false, updatable = false)
    override var resource: Resource? = null,

    @NotNull
    @Min(1)
    @Column(name = "amount", nullable = false, updatable = false)
    override var amount: Int? = null

) : EmbeddedIdEntity<ProductionId>(), ResourceAmountHolder

@Embeddable
data class ProductionId(
    val blueprintId: Int?,
    val resourceId: Int?
) : Serializable