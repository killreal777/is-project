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
    private Integer smallDocksQuantity; // full word 'small' for correct MapStruct mapping

    @NotNull
    @Min(0)
    @Column(name = "m_docks_quantity", nullable = false, updatable = false)
    private Integer mediumDocksQuantity; // full word 'medium' for correct MapStruct mapping

    @NotNull
    @Min(0)
    @Column(name = "l_docks_quantity", nullable = false, updatable = false)
    private Integer largeDocksQuantity; // full word 'large' for correct MapStruct mapping

    @AssertTrue
    public boolean containsAtLeastOneDock() {
        return smallDocksQuantity + mediumDocksQuantity + largeDocksQuantity > 0;
    }
}
