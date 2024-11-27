package itmo.is.project.dto.module.production;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductionModuleDto(
        @JsonProperty(value = "id", required = true)
        Integer id,

        @JsonProperty(value = "blueprint", required = true)
        ProductionModuleBlueprintDto productionModuleBlueprintDto
) {
}
