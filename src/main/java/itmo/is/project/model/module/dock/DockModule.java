package itmo.is.project.model.module.dock;

import itmo.is.project.model.module.Module;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "dock_module")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DockModule extends Module<DockModuleBlueprint> {


    @OneToMany(mappedBy = "dockModule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DockingSpot> dockingSpots;
}
