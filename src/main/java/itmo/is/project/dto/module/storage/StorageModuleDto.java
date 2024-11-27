package itmo.is.project.dto.module.storage;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StorageModuleDto(
        @JsonProperty(value = "id", required = true)
        Integer id,

        @JsonProperty(value = "blueprint", required = true)
        StorageModuleBlueprintDto blueprint
) {
}
