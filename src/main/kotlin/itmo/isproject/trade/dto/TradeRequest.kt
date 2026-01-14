package itmo.isproject.trade.dto

import com.fasterxml.jackson.annotation.JsonProperty
import itmo.isproject.shared.resource.dto.ResourceIdAmountDto

data class TradeRequest(

    @JsonProperty(value = "buy", required = true)
    val buy: List<ResourceIdAmountDto>,

    @JsonProperty(value = "sell", required = true)
    val sell: List<ResourceIdAmountDto>
)
