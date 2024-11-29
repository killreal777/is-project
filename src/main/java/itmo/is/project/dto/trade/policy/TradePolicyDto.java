package itmo.is.project.dto.trade.policy;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import itmo.is.project.model.resource.Resource;

public record TradePolicyDto(

        @JsonProperty(value = "resource", required = true)
        Resource resource,

        @Schema(example = "true")
        @JsonProperty(value = "stationSells", required = true)
        Boolean stationSells,

        @Schema(example = "5000")
        @JsonProperty(value = "sellPrice", required = true)
        Integer sellPrice,

        @Schema(example = "100")
        @JsonProperty(value = "sellLimit")
        Integer sellLimit,

        @Schema(example = "true")
        @JsonProperty(value = "stationBuys", required = true)
        Boolean stationBuys,

        @Schema(example = "500")
        @JsonProperty(value = "purchasePrice", required = true)
        Integer purchasePrice,

        @Schema(example = "10000")
        @JsonProperty(value = "purchaseLimit")
        Integer purchaseLimit
) {
}
