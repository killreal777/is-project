package itmo.is.project.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import itmo.is.project.model.user.Spaceship;

public record UpdateSpaceshipRequest(

        @Schema(example = "S")
        @JsonProperty(value = "size", required = true)
        Spaceship.Size size
) {
}
