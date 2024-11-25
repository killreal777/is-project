package itmo.is.project.dto.security;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RegistrationRequest(
        @JsonProperty(value = "username", required = true)
        String username,

        @JsonProperty(value = "password", required = true)
        String password
) {
}
