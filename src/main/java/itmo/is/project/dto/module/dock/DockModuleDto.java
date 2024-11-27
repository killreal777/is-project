package itmo.is.project.dto.module.dock;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DockModuleDto(
        @JsonProperty(value = "id", required = true)
        Integer id,

        @JsonProperty(value = "blueprint", required = true)
        DockModuleBlueprintDto blueprint
) {
}
