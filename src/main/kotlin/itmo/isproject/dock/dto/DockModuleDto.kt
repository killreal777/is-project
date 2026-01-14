package itmo.isproject.dock.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

data class DockModuleDto(

    @Schema(example = "1")
    @JsonProperty(value = "id", required = true)
    val id: Int?,

    @JsonProperty(value = "blueprint", required = true)
    val blueprint: DockModuleBlueprintDto,

    @JsonProperty(value = "dockingSpots", required = true)
    val dockingSpots: List<DockingSpotDto>
)
