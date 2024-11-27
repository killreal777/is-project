package itmo.is.project.dto.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import itmo.is.project.dto.resource.ResourceAmountDto;

import java.util.List;

public record BuildCostDto(
        @JsonProperty("id")
        Integer id,

        @JsonProperty("items")
        List<ResourceAmountDto> items
) {
}
