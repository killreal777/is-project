package itmo.isproject.dto.module.dock

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import itmo.isproject.dto.user.spaceship.SpaceshipDto
import itmo.isproject.model.user.Spaceship

data class DockingSpotDto(

    @Schema(example = "1")
    @JsonProperty(value = "id", required = true)
    val id: Int?,

    @Schema(example = "S")
    @JsonProperty(value = "size", required = true)
    val size: Spaceship.Size,

    @Schema(example = "true")
    @JsonProperty(value = "isOccupied", required = true)
    val isOccupied: Boolean,

    @JsonProperty(value = "spaceship")
    val spaceship: SpaceshipDto?
)
