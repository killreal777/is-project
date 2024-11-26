package itmo.is.project.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import itmo.is.project.model.user.Spaceship;

public record CreateSpaceshipRequest(
        @JsonProperty("size")
        Spaceship.Size size
) {
}
