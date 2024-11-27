package itmo.is.project.dto.security;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JwtResponse(
        @JsonProperty("accessToken")
        String accessToken
) {
}
