package itmo.is.project.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import itmo.is.project.model.resource.ResourceIdAmount;

import java.util.Set;

public record TradeRequest(

        @JsonProperty(value = "buy", required = true)
        Set<ResourceIdAmount> buy,

        @JsonProperty(value = "sell", required = true)
        Set<ResourceIdAmount> sell
) {
}
