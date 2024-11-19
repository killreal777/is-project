package itmo.is.project.model.module.storage;

import itmo.is.project.model.Resource;
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
public class StoredResource {

    @Embeddable
    public record CompositeKey (Integer storageModuleId, Integer resourceId) {}

    @EmbeddedId
    private StoredResource.CompositeKey id;

    @MapsId("storageModuleId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id", referencedColumnName = "id")
    private StorageModule storage;

    @MapsId("resourceId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resource_id", referencedColumnName = "id")
    private Resource resource;

    @NotNull
    @Min(1)
    @Column(name = "amount", nullable = false)
    private Integer amount;
}
