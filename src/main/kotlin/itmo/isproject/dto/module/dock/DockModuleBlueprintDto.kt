package itmo.isproject.dto.module.dock

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import itmo.isproject.dto.module.BuildCostDto

data class DockModuleBlueprintDto(

    @Schema(example = "1")
    @JsonProperty(value = "id", required = true)
    val id: Int?,

    @Schema(example = "Dock Area 1M6S")
    @JsonProperty(value = "name", required = true)
    val name: String,

    @JsonProperty(value = "buildCost", required = true)
    val buildCost: BuildCostDto,

    @Schema(example = "6")
    @JsonProperty(value = "sDocksQuantity", required = true)
    val smallDocksQuantity: Int, // full word 'small' for correct MapStruct mapping

    @Schema(example = "1")
    @JsonProperty(value = "mDocksQuantity", required = true)
    val mediumDocksQuantity: Int, // full word 'medium' for correct MapStruct mapping

    @Schema(example = "0")
    @JsonProperty(value = "lDocksQuantity", required = true)
    val largeDocksQuantity: Int // full word 'large' for correct MapStruct mapping
)
