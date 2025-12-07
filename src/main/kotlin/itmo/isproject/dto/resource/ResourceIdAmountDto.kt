package itmo.isproject.dto.resource

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

data class ResourceIdAmountDto(

    @Schema(example = "8")
    @JsonProperty(value = "id", required = true)
    val id: Int?,

    @Schema(example = "500")
    @JsonProperty(value = "amount", required = true)
    val amount: Int?
)
