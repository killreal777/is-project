package itmo.isproject.dto.user.spaceship

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import itmo.isproject.dto.user.UserDto
import itmo.isproject.model.user.Spaceship

data class SpaceshipDto(

    @Schema(example = "1")
    @JsonProperty(value = "id", required = true)
    val id: Int?,

    @JsonProperty(value = "pilot", required = true)
    val pilot: UserDto,

    @Schema(example = "S")
    @JsonProperty(value = "size", required = true)
    val size: Spaceship.Size
)
