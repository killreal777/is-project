package itmo.isproject.dto.module

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import itmo.isproject.dto.resource.ResourceAmountDto

data class BuildCostDto(

    @Schema(example = "1")
    @JsonProperty(value = "id", required = true)
    val id: Int?,

    @JsonProperty(value = "items", required = true)
    val items: List<ResourceAmountDto>
)
