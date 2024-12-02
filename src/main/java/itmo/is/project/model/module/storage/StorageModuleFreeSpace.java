package itmo.is.project.model.module.storage;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.util.Pair;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StorageModuleFreeSpace {

    @NotNull
    private StorageModule storageModule;

    @Min(0)
    @NotNull
    private Integer freeSpace;

    public StorageModuleFreeSpace(Pair<StorageModule, Integer> storageModuleFreeSpacePair) {
        this.storageModule = storageModuleFreeSpacePair.getFirst();
        this.freeSpace = storageModuleFreeSpacePair.getSecond();
    }

    public Integer getStorageModuleId() {
        return storageModule.getId();
    }

    public void add(int space) {
        freeSpace += space;
    }

    public void sub(int space) {
        freeSpace -= space;
    }
}
