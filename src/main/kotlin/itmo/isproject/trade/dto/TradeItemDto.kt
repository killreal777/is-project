package itmo.isproject.trade.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import itmo.isproject.shared.resource.dto.ResourceDto
import itmo.isproject.trade.model.Operation

data class TradeItemDto(

    @Schema(example = "1")
    @JsonProperty(value = "tradeId", required = true)
    val tradeId: Int?,

    @JsonProperty(value = "resource", required = true)
    val resource: ResourceDto,

    @Schema(example = "500")
    @JsonProperty(value = "amount", required = true)
    val amount: Int?,

    @Schema(example = "SELL")
    @JsonProperty(value = "operation", required = true)
    val operation: Operation,

    @Schema(example = "1000")
    @JsonProperty(value = "price", required = true)
    val price: Int?
)
