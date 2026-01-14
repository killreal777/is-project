package itmo.isproject.shared.module.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

data class BuildModuleRequest(

    @Schema(example = "1")
    @JsonProperty(value = "blueprintId", required = true)
    val blueprintId: Int
)
