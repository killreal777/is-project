package itmo.is.project.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import itmo.is.project.dto.resource.ResourceDto;
import itmo.is.project.model.trade.Operation;

public record TradeItemDto(

        @Schema(example = "1")
        @JsonProperty(value = "tradeId", required = true)
        Integer tradeId,

        @JsonProperty(value = "resource", required = true)
        ResourceDto resource,

        @Schema(example = "500")
        @JsonProperty(value = "amount", required = true)
        Integer amount,

        @Schema(example = "SELL")
        @JsonProperty(value = "operation", required = true)
        Operation operation,

        @Schema(example = "1000")
        @JsonProperty(value = "price", required = true)
        Integer price
) {
}
