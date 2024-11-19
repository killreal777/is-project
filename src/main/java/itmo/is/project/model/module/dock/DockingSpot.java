package itmo.is.project.model.module.dock;

import itmo.is.project.model.spaceship.Spaceship;
import itmo.is.project.model.spaceship.SpaceshipSize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "docking_spot")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DockingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dock_module_id", referencedColumnName = "id", nullable = false, updatable = false)
    private DockModule dockModule;

    @NotNull
    @Column(name = "size", length = 1, nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private SpaceshipSize size;

    @NotNull
    @Column(name = "is_occupied", nullable = false)
    private Boolean isOccupied = false;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "spaceship_id", referencedColumnName = "id", unique = true)
    private Spaceship spaceship;
}