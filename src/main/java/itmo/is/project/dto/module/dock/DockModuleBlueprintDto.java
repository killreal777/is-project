package itmo.is.project.dto.module.dock;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import itmo.is.project.dto.module.BuildCostDto;

public record DockModuleBlueprintDto(

        @Schema(example = "1")
        @JsonProperty(value = "id", required = true)
        Integer id,

        @Schema(example = "Dock Area 1M6S")
        @JsonProperty(value = "name", required = true)
        String name,

        @JsonProperty(value = "buildCost", required = true)
        BuildCostDto buildCost,

        @Schema(example = "6")
        @JsonProperty(value = "sDocksQuantity", required = true)
        Integer smallDocksQuantity, // full word 'small' for correct MapStruct mapping

        @Schema(example = "1")
        @JsonProperty(value = "mDocksQuantity", required = true)
        Integer mediumDocksQuantity, // full word 'medium' for correct MapStruct mapping

        @Schema(example = "0")
        @JsonProperty(value = "lDocksQuantity", required = true)
        Integer largeDocksQuantity // full word 'large' for correct MapStruct mapping
) {
}
