package itmo.is.project.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record TransferRequest(

        @Schema(example = "5000")
        @JsonProperty(value = "amount", required = true)
        Integer amount
) {
}
