package itmo.is.project.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record AccountDto (

        @JsonProperty(value = "user", required = true)
        UserDto user,

        @Schema(example = "1000000")
        @JsonProperty(value = "balance", required = true)
        Integer balance
) {
}
