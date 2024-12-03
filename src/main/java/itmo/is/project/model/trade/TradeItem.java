package itmo.is.project.model.trade;

import itmo.is.project.model.resource.Resource;
import itmo.is.project.model.resource.ResourceAmountHolder;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resource_id", referencedColumnName = "id")
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

    public TradeItem(Trade trade, ResourceAmountHolder resourceAmount, Operation operation, Integer price) {
        setCompositeKey(trade, resourceAmount.getResource());
        this.amount = resourceAmount.getAmount();
        this.operation = operation;
        this.price = price;
    }

    public void setCompositeKey(Trade trade, Resource resource) {
        this.trade = trade;
        this.resource = resource;
        this.id = new TradeItem.CompositeKey(trade.getId(), resource.getId());
    }
}
