package itmo.is.project.dto.user.spaceship;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import itmo.is.project.dto.user.UserDto;
import itmo.is.project.model.user.Spaceship;

public record SpaceshipDto(

        @Schema(example = "1")
        @JsonProperty(value = "id", required = true)
        Integer id,

        @JsonProperty(value = "pilot", required = true)
        UserDto pilot,

        @Schema(example = "S")
        @JsonProperty(value = "size", required = true)
        Spaceship.Size size
) {
}
