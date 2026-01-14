package itmo.isproject.shared.module.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import itmo.isproject.shared.resource.dto.ResourceAmountDto

data class BuildCostDto(

    @Schema(example = "1")
    @JsonProperty(value = "id", required = true)
    val id: Int?,

    @JsonProperty(value = "items", required = true)
    val items: List<ResourceAmountDto>
)
