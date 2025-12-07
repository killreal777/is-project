package itmo.isproject.dto.user.spaceship

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import itmo.isproject.model.user.Spaceship

data class CreateSpaceshipRequest(

    @Schema(example = "S")
    @JsonProperty(value = "size", required = true)
    val size: Spaceship.Size
)
