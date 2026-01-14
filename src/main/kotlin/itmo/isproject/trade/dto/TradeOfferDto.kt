package itmo.isproject.trade.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import itmo.isproject.shared.resource.model.Resource

data class TradeOfferDto(

    @JsonProperty(value = "resource", required = true)
    val resource: Resource,

    @Schema(example = "200")
    @JsonProperty(value = "amount", required = true)
    val amount: Int?,

    @Schema(example = "1000")
    @JsonProperty(value = "price", required = true)
    val price: Int?
)
