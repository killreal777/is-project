package itmo.is.project.dto.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record ModuleBlueprintDto(

        @Schema(example = "1")
        @JsonProperty(value = "id", required = true)
        Integer id,

        @Schema(example = "Advanced Composite Production")
        @JsonProperty(value = "name", required = true)
        String name,

        @JsonProperty(value = "buildCost", required = true)
        BuildCostDto buildCost
) {
}
