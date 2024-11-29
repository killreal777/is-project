package itmo.is.project.dto.module.dock;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import itmo.is.project.dto.user.SpaceshipDto;
import itmo.is.project.model.user.Spaceship;

public record DockingSpotDto(

        @Schema(example = "1")
        @JsonProperty(value = "id", required = true)
        Integer id,

        @Schema(example = "S")
        @JsonProperty(value = "size", required = true)
        Spaceship.Size size,

        @Schema(example = "true")
        @JsonProperty(value = "isOccupied", required = true)
        boolean isOccupied,

        @JsonProperty(value = "spaceship")
        SpaceshipDto spaceship
) {
}
