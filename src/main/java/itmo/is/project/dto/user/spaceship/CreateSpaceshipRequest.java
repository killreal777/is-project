package itmo.is.project.dto.user.spaceship;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import itmo.is.project.model.user.Spaceship;

public record CreateSpaceshipRequest(

        @Schema(example = "S")
        @JsonProperty(value = "size", required = true)
        Spaceship.Size size
) {
}
