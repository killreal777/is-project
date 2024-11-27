package itmo.is.project.dto.module.dock;

import com.fasterxml.jackson.annotation.JsonProperty;
import itmo.is.project.dto.module.BuildCostDto;

public record DockModuleBlueprintDto(
        @JsonProperty("id")
        Integer id,

        @JsonProperty("name")
        String name,

        @JsonProperty("buildCost")
        BuildCostDto buildCost,

        @JsonProperty("sDocksQuantity")
        Integer smallDocksQuantity, // full word 'small' for correct MapStruct mapping

        @JsonProperty("mDocksQuantity")
        Integer mediumDocksQuantity, // full word 'medium' for correct MapStruct mapping

        @JsonProperty("lDocksQuantity")
        Integer largeDocksQuantity // full word 'large' for correct MapStruct mapping
) {
}
