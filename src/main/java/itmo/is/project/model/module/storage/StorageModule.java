package itmo.is.project.model.module.storage;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "storage_module")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StorageModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "blueprint_id", referencedColumnName = "id", nullable = false, updatable = false)
    private StorageModuleBlueprint blueprint;

    @OneToMany(mappedBy = "storage", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoredResource> storedResources;
}

