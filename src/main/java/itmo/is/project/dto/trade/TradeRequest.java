package itmo.is.project.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import itmo.is.project.model.resource.ResourceIdAmount;

import java.util.Set;

public record TradeRequest(

        @JsonProperty(value = "buyFromStation", required = true)
        Set<ResourceIdAmount> buyFromStation,

        @JsonProperty(value = "sellToStation", required = true)
        Set<ResourceIdAmount> sellToStation
) {
}
