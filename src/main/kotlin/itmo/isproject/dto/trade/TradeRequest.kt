package itmo.isproject.dto.trade

import com.fasterxml.jackson.annotation.JsonProperty
import itmo.isproject.dto.resource.ResourceIdAmountDto

data class TradeRequest(

    @JsonProperty(value = "buy", required = true)
    val buy: List<ResourceIdAmountDto>,

    @JsonProperty(value = "sell", required = true)
    val sell: List<ResourceIdAmountDto>
)
