package itmo.is.project.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import itmo.is.project.dto.resource.ResourceIdAmountDto;

import java.util.List;

public record TradeRequest(

        @JsonProperty(value = "buy", required = true)
        List<ResourceIdAmountDto> buy,

        @JsonProperty(value = "sell", required = true)
        List<ResourceIdAmountDto> sell
) {
}
