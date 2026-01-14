package itmo.isproject.shared.resource.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

data class ResourceDto(

    @Schema(example = "8")
    @JsonProperty(value = "id", required = true)
    val id: Int?,

    @Schema(example = "Claytronics")
    @JsonProperty(value = "name", required = true)
    val name: String
)
