package itmo.is.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record AmountDto(

        @Schema(example = "100")
        @JsonProperty(value = "amount", required = true)
        Integer amount
) {
}
