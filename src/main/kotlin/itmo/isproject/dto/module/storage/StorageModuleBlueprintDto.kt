package itmo.isproject.dto.module.storage

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import itmo.isproject.dto.module.BuildCostDto

data class StorageModuleBlueprintDto(

    @Schema(example = "1")
    @JsonProperty(value = "id", required = true)
    val id: Int?,

    @Schema(example = "Storage S")
    @JsonProperty(value = "name", required = true)
    val name: String,

    @JsonProperty(value = "buildCost", required = true)
    val buildCost: BuildCostDto,

    @Schema(example = "25000")
    @JsonProperty(value = "capacity", required = true)
    val capacity: Int?
)
