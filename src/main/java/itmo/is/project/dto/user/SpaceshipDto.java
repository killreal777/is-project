package itmo.is.project.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import itmo.is.project.model.user.Spaceship;

public record SpaceshipDto(
        @JsonProperty("id")
        Integer id,

        @JsonProperty("pilot")
        UserDto pilot,

        @JsonProperty("size")
        Spaceship.Size size
) {
}
