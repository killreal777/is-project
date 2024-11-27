package itmo.is.project.dto.module.storage;

import com.fasterxml.jackson.annotation.JsonProperty;
import itmo.is.project.dto.module.BuildCostDto;

public record StorageModuleBlueprintDto(
        @JsonProperty("id")
        Integer id,

        @JsonProperty("name")
        String name,

        @JsonProperty("buildCost")
        BuildCostDto buildCost,

        @JsonProperty("capacity")
        Integer capacity
) {
}
