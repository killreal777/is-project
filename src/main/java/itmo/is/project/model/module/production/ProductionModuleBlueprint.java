package itmo.is.project.model.module.production;

import itmo.is.project.model.module.ModuleBlueprint;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "production_module_blueprint")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductionModuleBlueprint extends ModuleBlueprint {

    @OneToOne(mappedBy = "blueprint", cascade = CascadeType.ALL)
    private Production production;

    @OneToMany(mappedBy = "blueprint", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Consumption> consumption;
}

