package itmo.is.project.dto.module.storage;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import itmo.is.project.dto.module.BuildCostDto;

public record StorageModuleBlueprintDto(

        @Schema(example = "1")
        @JsonProperty(value = "id", required = true)
        Integer id,

        @Schema(example = "Storage S")
        @JsonProperty(value = "name", required = true)
        String name,

        @JsonProperty(value = "buildCost", required = true)
        BuildCostDto buildCost,

        @Schema(example = "25000")
        @JsonProperty(value = "capacity", required = true)
        Integer capacity
) {
}
