package itmo.is.project.dto.module.production;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import itmo.is.project.dto.module.BuildCostDto;
import itmo.is.project.dto.resource.ResourceAmountDto;

import java.util.List;

public record ProductionModuleBlueprintDto(

        @Schema(example = "1")
        @JsonProperty(value = "id", required = true)
        Integer id,

        @Schema(example = "1")
        @JsonProperty(value = "Advanced Composite Production", required = true)
        String name,

        @JsonProperty(value = "buildCost", required = true)
        BuildCostDto buildCost,

        @JsonProperty(value = "production", required = true)
        ResourceAmountDto production,

        @JsonProperty(value = "consumption", required = true)
        List<ResourceAmountDto> consumption
) {
}
