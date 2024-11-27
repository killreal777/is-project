package itmo.is.project.dto.module;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ModuleBlueprintDto(
        @JsonProperty("id")
        Integer id,

        @JsonProperty("name")
        String name,

        @JsonProperty("buildCost")
        BuildCostDto buildCost
) {
}
