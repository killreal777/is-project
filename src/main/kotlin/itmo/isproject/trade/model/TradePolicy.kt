package itmo.isproject.trade.model

import itmo.isproject.shared.resource.model.Resource
import jakarta.persistence.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "trade_policy")
class TradePolicy(

    @MapsId
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resource_id", referencedColumnName = "id")
    var resource: Resource? = null,

    @NotNull
    @Column(name = "station_sells", nullable = false)
    var stationSells: Boolean = false,

    @NotNull
    @Min(0)
    @Column(name = "sell_price", nullable = false)
    var sellPrice: Int = 0,

    @Min(0)
    @Column(name = "sell_limit")
    var sellLimit: Int? = null,

    @NotNull
    @Column(name = "station_buys", nullable = false)
    var stationBuys: Boolean = false,

    @NotNull
    @Min(0)
    @Column(name = "purchase_price", nullable = false)
    var purchasePrice: Int = 0,

    @Min(0)
    @Column(name = "purchase_limit")
    var purchaseLimit: Int? = null

) {
    @Id
    var resourceId: Int? = null
        get() = resource?.id
        set(value) {
            field = value
            resource = null
        }

    init {
        resourceId = resource?.id
    }
}
