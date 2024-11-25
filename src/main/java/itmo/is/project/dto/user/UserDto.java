package itmo.is.project.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import itmo.is.project.model.user.Role;

public record UserDto(
        @JsonProperty("id")
        Long id,

        @JsonProperty("username")
        String username,

        @JsonProperty("role")
        Role role
) {
}
