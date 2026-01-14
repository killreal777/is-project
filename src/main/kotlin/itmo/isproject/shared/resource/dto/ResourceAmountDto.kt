package itmo.isproject.shared.resource.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

data class ResourceAmountDto(

    @JsonProperty(value = "resource", required = true)
    val resource: ResourceDto,

    @Schema(example = "500")
    @JsonProperty(value = "amount", required = true)
    val amount: Int?
)
