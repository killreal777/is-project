package itmo.is.project.model.module.storage;

import itmo.is.project.model.resource.Resource;
import itmo.is.project.model.resource.ResourceAmount;
import itmo.is.project.model.resource.ResourceAmountHolder;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "stored_resource")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoredResource implements ResourceAmountHolder {

    @Embeddable
    public record CompositeKey(Integer storageModuleId, Integer resourceId) {
    }

    @EmbeddedId
    private StoredResource.CompositeKey id;

    @MapsId("storageModuleId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id", referencedColumnName = "id")
    private StorageModule storageModule;

    @MapsId("resourceId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resource_id", referencedColumnName = "id")
    private Resource resource;

    @NotNull
    @Min(1)
    @Column(name = "amount", nullable = false)
    private Integer amount;

    public StoredResource(ResourceAmount resourceAmount, StorageModule storageModule) {
        setResourceAmount(resourceAmount);
        setStorageModule(storageModule);
        setId(new CompositeKey(storageModule.getId(), resource.getId()));
    }

    public void add(Integer amount) {
        this.amount += amount;
    }

    public void subtract(Integer amount) {
        this.amount -= amount;
    }
}
