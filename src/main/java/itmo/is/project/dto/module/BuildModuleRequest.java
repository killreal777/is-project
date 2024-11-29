package itmo.is.project.dto.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record BuildModuleRequest(

        @Schema(example = "1")
        @JsonProperty(value = "blueprintId", required = true)
        Integer blueprintId
) {
}
