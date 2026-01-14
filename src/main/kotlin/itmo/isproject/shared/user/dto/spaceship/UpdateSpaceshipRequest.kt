package itmo.isproject.shared.user.dto.spaceship

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import itmo.isproject.shared.user.model.Spaceship

data class UpdateSpaceshipRequest(

    @Schema(example = "S")
    @JsonProperty(value = "size", required = true)
    val size: Spaceship.Size
)
