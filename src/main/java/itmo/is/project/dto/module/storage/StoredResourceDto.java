package itmo.is.project.dto.module.storage;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import itmo.is.project.dto.resource.ResourceAmountDto;

public record StoredResourceDto(

        @Schema(example = "1")
        @JsonProperty(value = "storageModuleId", required = true)
        Integer storageModuleId,

        @JsonProperty(value = "resourceAmount", required = true)
        ResourceAmountDto resourceAmount
) {
}
