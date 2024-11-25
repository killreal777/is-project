package itmo.is.project.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TransferRequest(
        @JsonProperty("amount")
        Integer amount
) {
}
