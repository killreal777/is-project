package itmo.is.project.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import itmo.is.project.dto.user.UserDto;

import java.time.LocalDateTime;

public record TradeDto(

        @Schema(example = "1")
        @JsonProperty(value = "id", required = true)
        Integer id,

        @JsonProperty(value = "user", required = true)
        UserDto user,

        @Schema(example = "500")
        @JsonProperty(value = "time", required = true)
        LocalDateTime time
) {
}
