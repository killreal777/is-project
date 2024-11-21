package itmo.is.project.model.module;

import itmo.is.project.model.resource.Resource;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "build_cost_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuildCostItem {

    @Embeddable
    public record CompositeKey(Integer buildCostId, Integer resourceId) {
    }

    @EmbeddedId
    private BuildCostItem.CompositeKey id;

    @MapsId("buildCostId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "build_cost_id", referencedColumnName = "id")
    private BuildCost buildCost;

    @MapsId("resourceId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resource_id", referencedColumnName = "id")
    private Resource resource;

    @NotNull
    @Min(1)
    @Column(name = "amount", nullable = false, updatable = false)
    private Integer amount;
}

