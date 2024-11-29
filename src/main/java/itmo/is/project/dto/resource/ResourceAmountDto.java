package itmo.is.project.dto.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record ResourceAmountDto(

        @JsonProperty(value = "resource", required = true)
        ResourceDto resource,

        @Schema(example = "500")
        @JsonProperty(value = "amount", required = true)
        Integer amount
) {
}
