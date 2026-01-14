package itmo.isproject.production.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import itmo.isproject.shared.module.dto.BuildCostDto
import itmo.isproject.shared.resource.dto.ResourceAmountDto

data class ProductionModuleBlueprintDto(

    @Schema(example = "1")
    @JsonProperty(value = "id", required = true)
    val id: Int?,

    @Schema(example = "Advanced Composite Production")
    @JsonProperty(value = "name", required = true)
    val name: String,

    @JsonProperty(value = "buildCost", required = true)
    val buildCost: BuildCostDto,

    @JsonProperty(value = "production", required = true)
    val production: ResourceAmountDto,

    @JsonProperty(value = "consumption", required = true)
    val consumption: List<ResourceAmountDto>
)
