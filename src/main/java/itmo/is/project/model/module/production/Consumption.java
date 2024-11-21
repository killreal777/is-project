package itmo.is.project.model.module.production;

import itmo.is.project.model.resource.Resource;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "production")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Consumption {

    @Embeddable
    public record CompositeKey(Integer blueprintId, Integer resourceId) {
    }

    @EmbeddedId
    private Consumption.CompositeKey id;

    @MapsId("blueprintId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "blueprint_id", referencedColumnName = "id")
    private ProductionModuleBlueprint blueprint;

    @MapsId("resourceId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resource_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Resource resource;

    @NotNull
    @Min(1)
    @Column(name = "amount", nullable = false, updatable = false)
    private Integer amount;
}
