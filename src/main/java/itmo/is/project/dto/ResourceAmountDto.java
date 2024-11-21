package itmo.is.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResourceAmountDto(

        @JsonProperty(value = "resource")
        ResourceDto resource,

        @JsonProperty(value = "amount")
        Integer amount
) {
}
