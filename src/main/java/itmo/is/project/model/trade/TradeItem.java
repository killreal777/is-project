package itmo.is.project.model.trade;

import itmo.is.project.model.resource.Resource;
import itmo.is.project.model.resource.ResourceAmountHolder;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trade_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TradeItem implements ResourceAmountHolder {

    @Embeddable
    public record CompositeKey(Integer tradeId, Integer resourceId) {
    }

    @EmbeddedId
    private TradeItem.CompositeKey id;

    @MapsId("tradeId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_id", referencedColumnName = "id")
    private Trade trade;

    @MapsId("resourceId")
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resource_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Resource resource;

    @NotNull
    @Min(1)
    @Column(name = "amount", nullable = false, updatable = false)
    private Integer amount;

    @NotNull
    @Column(name = "operation", length = 4, nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Operation operation;

    @NotNull
    @Min(0)
    @Column(name = "price", nullable = false, updatable = false)
    private Integer price;
}
