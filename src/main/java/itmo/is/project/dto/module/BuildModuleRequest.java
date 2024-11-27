package itmo.is.project.dto.module;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BuildModuleRequest(
        @JsonProperty(value = "blueprintId", required = true)
        Integer blueprintId
) {
}
