package itmo.is.project.model.module.production;

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
public class ProductionModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "blueprint_id", referencedColumnName = "id", nullable = false, updatable = false)
    private ProductionModuleBlueprint blueprint;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private ProductionModuleState state = ProductionModuleState.OFF;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "engineer_id", referencedColumnName = "id", unique = true)
    private User engineer;
}
