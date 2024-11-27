package itmo.is.project.dto.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import itmo.is.project.dto.module.storage.StorageModuleDto;

public record StoredResourceDto(
        @JsonProperty("storage")
        StorageModuleDto storage,

        @JsonProperty("resourceAmount")
        ResourceAmountDto resourceAmount
) {
}
