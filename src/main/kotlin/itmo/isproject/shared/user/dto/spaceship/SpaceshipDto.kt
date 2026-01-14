package itmo.isproject.shared.user.dto.spaceship

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import itmo.isproject.shared.user.dto.UserDto
import itmo.isproject.shared.user.model.Spaceship

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
