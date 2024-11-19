package itmo.is.project.model.module.storage;

import itmo.is.project.model.module.ModuleBlueprint;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "storage_module_blueprint")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StorageModuleBlueprint extends ModuleBlueprint {

    @NotNull
    @Min(1)
    @Column(name = "capacity", nullable = false, updatable = false)
    private Integer capacity;
}
