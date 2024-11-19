package itmo.is.project.model.module.dock;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "dock_module")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DockModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "blueprint_id", referencedColumnName = "id", nullable = false, updatable = false)
    private DockModuleBlueprint blueprint;

    @OneToMany(mappedBy = "dockModule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DockingSpot> dockingSpots;
}
