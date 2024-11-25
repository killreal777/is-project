package itmo.is.project.dto.security;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JwtResponse(
        @JsonProperty("access_token")
        String accessToken
) {
}
