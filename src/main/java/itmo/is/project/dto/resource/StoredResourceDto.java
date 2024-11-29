package itmo.is.project.dto.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record StoredResourceDto(

        @Schema(example = "1")
        @JsonProperty(value = "storageId", required = true)
        Integer storageModuleId,

        @JsonProperty(value = "resourceAmount", required = true)
        ResourceAmountDto resourceAmount
) {
}
