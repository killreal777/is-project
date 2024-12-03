package itmo.is.project.model.trade;

import itmo.is.project.model.resource.Resource;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "trade_policy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TradePolicy {

    @Id
    private Integer resourceId;

    @MapsId
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resource_id", referencedColumnName = "id")
    private Resource resource;

    @NotNull
    @Column(name = "station_sells", nullable = false)
    private Boolean stationSells = false;

    @NotNull
    @Min(0)
    @Column(name = "sell_price", nullable = false)
    private Integer sellPrice = 0;

    @Min(0)
    @Column(name = "sell_limit")
    private Integer sellLimit;

    @NotNull
    @Column(name = "station_buys", nullable = false)
    private Boolean stationBuys = false;

    @NotNull
    @Min(0)
    @Column(name = "purchase_price", nullable = false)
    private Integer purchasePrice = 0;

    @Min(0)
    @Column(name = "purchase_limit")
    private Integer purchaseLimit;

    public void setResource(Resource resource) {
        this.resource = resource;
        this.resourceId = resource.getId();
    }
}
