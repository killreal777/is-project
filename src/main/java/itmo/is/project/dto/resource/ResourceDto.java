package itmo.is.project.dto.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResourceDto(
        @JsonProperty("id")
        Integer id,

        @JsonProperty("name")
        String name
) {
}
