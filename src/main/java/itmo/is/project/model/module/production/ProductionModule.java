package itmo.is.project.model.module.production;

import itmo.is.project.model.module.Module;
import itmo.is.project.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "production_module")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductionModule extends Module<ProductionModuleBlueprint> {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private ProductionModuleState state = ProductionModuleState.OFF;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "engineer_id", referencedColumnName = "id", unique = true)
    private User engineer;
}
