package itmo.isproject.storage.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import itmo.isproject.shared.resource.dto.ResourceAmountDto

data class StoredResourceDto(

    @Schema(example = "1")
    @JsonProperty(value = "storageModuleId", required = true)
    val storageModuleId: Int?,

    @JsonProperty(value = "resourceAmount", required = true)
    val resourceAmount: ResourceAmountDto
)
