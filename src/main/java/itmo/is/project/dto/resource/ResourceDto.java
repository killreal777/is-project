package itmo.is.project.dto.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record ResourceDto(

        @Schema(example = "8")
        @JsonProperty(value = "id", required = true)
        Integer id,

        @Schema(example = "Claytronics")
        @JsonProperty(value = "name", required = true)
        String name
) {
}
