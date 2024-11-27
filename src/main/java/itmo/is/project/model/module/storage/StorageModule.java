package itmo.is.project.model.module.storage;

import itmo.is.project.model.module.Module;
import itmo.is.project.model.resource.ResourceAmount;
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

    @OneToMany(mappedBy = "storage", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoredResource> storedResources;

    public void addNewResource(ResourceAmount resourceAmount) {
        StoredResource storedResource = new StoredResource(resourceAmount, this);
        storedResources.add(storedResource);
    }
}

