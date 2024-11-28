package itmo.is.project.dto.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResourceTransferDto(
        @JsonProperty("resourceId")
        Integer resourceId,

        @JsonProperty("amount")
        Integer amount
) {
}
