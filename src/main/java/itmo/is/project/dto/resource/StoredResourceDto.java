package itmo.is.project.dto.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StoredResourceDto(
        @JsonProperty("storageId")
        Integer storageModuleId,

        @JsonProperty("resourceAmount")
        ResourceAmountDto resourceAmount
) {
}
