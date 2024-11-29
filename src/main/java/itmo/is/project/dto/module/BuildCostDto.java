package itmo.is.project.dto.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import itmo.is.project.dto.resource.ResourceAmountDto;

import java.util.List;

public record BuildCostDto(

        @Schema(example = "1")
        @JsonProperty(value = "id", required = true)
        Integer id,

        @JsonProperty(value = "items", required = true)
        List<ResourceAmountDto> items
) {
}
