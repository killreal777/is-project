package itmo.is.project.dto.module.production;

import com.fasterxml.jackson.annotation.JsonProperty;
import itmo.is.project.dto.module.BuildCostDto;
import itmo.is.project.dto.resource.ResourceAmountDto;

import java.util.List;

public record ProductionModuleBlueprintDto(
        @JsonProperty("id")
        Integer id,

        @JsonProperty("name")
        String name,

        @JsonProperty("buildCost")
        BuildCostDto buildCost,

        @JsonProperty("production")
        ResourceAmountDto production,

        @JsonProperty("consumption")
        List<ResourceAmountDto> consumption
) {
}
