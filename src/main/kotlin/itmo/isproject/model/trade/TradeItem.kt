package itmo.isproject.model.trade

import itmo.isproject.model.EmbeddedIdEntity
import itmo.isproject.model.resource.Resource
import itmo.isproject.model.resource.ResourceAmountHolder
import jakarta.persistence.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.io.Serializable

@Entity
@Table(name = "trade_item")
class TradeItem(

    @MapsId("tradeId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_id", referencedColumnName = "id")
    var trade: Trade? = null,

    @MapsId("resourceId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resource_id", referencedColumnName = "id")
    override var resource: Resource? = null,

    @NotNull
    @Min(1)
    @Column(name = "amount", nullable = false, updatable = false)
    override var amount: Int? = null,

    @NotNull
    @Column(name = "operation", length = 4, nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    var operation: Operation? = null,

    @NotNull
    @Min(0)
    @Column(name = "price", nullable = false, updatable = false)
    var price: Int? = null

) : EmbeddedIdEntity<TradeItemId>(), ResourceAmountHolder {

    constructor(trade: Trade?, resourceAmount: ResourceAmountHolder, operation: Operation?, price: Int?) : this(
        trade = trade,
        resource = resourceAmount.resource,
        amount = resourceAmount.amount,
        operation = operation,
        price = price
    ) {
        id = TradeItemId(trade?.id, resource?.id)
    }
}

@Embeddable
data class TradeItemId(
    val tradeId: Int?,
    val resourceId: Int?
) : Serializable
