package itmo.is.project.dto.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record ResourceIdAmountDto(

        @Schema(example = "8")
        @JsonProperty(value = "id", required = true)
        Integer id,

        @Schema(example = "500")
        @JsonProperty(value = "amount", required = true)
        Integer amount
) {
}
