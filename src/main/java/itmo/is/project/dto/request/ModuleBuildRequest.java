package itmo.is.project.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ModuleBuildRequest(
        @JsonProperty(value = "blueprint_id", required = true)
        Integer blueprintId
) {
}
