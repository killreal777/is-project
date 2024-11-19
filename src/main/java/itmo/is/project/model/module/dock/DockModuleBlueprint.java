package itmo.is.project.model.module.dock;

import itmo.is.project.model.module.ModuleBlueprint;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "dock_module_blueprint")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DockModuleBlueprint extends ModuleBlueprint {

    @NotNull
    @Min(0)
    @Column(name = "s_docks_quantity", nullable = false, updatable = false)
    private Integer sDocksQuantity;

    @NotNull
    @Min(0)
    @Column(name = "m_docks_quantity", nullable = false, updatable = false)
    private Integer mDocksQuantity;

    @NotNull
    @Min(0)
    @Column(name = "l_docks_quantity", nullable = false, updatable = false)
    private Integer lDocksQuantity;

    @AssertTrue
    public boolean containsAtLeastOneDock() {
        return sDocksQuantity + mDocksQuantity + lDocksQuantity > 0;
    }
}
