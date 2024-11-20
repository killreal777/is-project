package itmo.is.project.model.module;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class ModuleBlueprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "build_cost_id", referencedColumnName = "id", nullable = false, unique = true, updatable = false)
    private BuildCost buildCost;

    @NotBlank
    @Size(max = 48)
    @Column(name = "name", length = 48, nullable = false, unique = true, updatable = false)
    private String name;
}
