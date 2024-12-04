package itmo.is.project.dto.module.storage;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record StorageModuleDto(

        @Schema(example = "1")
        @JsonProperty(value = "id", required = true)
        Integer id,

        @JsonProperty(value = "blueprint", required = true)
        StorageModuleBlueprintDto blueprint,

        @JsonProperty(value = "storedResources", required = true)
        List<StoredResourceDto> storedResources
) {
}
