package itmo.isproject.storage.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

data class StorageModuleDto(

    @Schema(example = "1")
    @JsonProperty(value = "id", required = true)
    val id: Int?,

    @JsonProperty(value = "blueprint", required = true)
    val blueprint: StorageModuleBlueprintDto,

    @JsonProperty(value = "storedResources", required = true)
    val storedResources: List<StoredResourceDto>
)
