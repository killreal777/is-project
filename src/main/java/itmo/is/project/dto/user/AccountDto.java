package itmo.is.project.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccountDto (
        @JsonProperty("user")
        UserDto user,

        @JsonProperty("balance")
        Integer balance
) {
}
