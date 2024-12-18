package itmo.is.project.model.module.storage;

import itmo.is.project.model.module.Module;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "storage_module")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StorageModule extends Module<StorageModuleBlueprint> {

    @OneToMany(mappedBy = "storageModule", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoredResource> storedResources;
}

