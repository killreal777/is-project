package itmo.isproject.dto.trade.policy

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

data class UpdateTradePolicyRequest(

    @Schema(example = "true")
    @JsonProperty(value = "stationSells", required = true)
    val stationSells: Boolean,

    @Schema(example = "5000")
    @JsonProperty(value = "sellPrice", required = true)
    val sellPrice: Int?,

    @Schema(example = "100")
    @JsonProperty(value = "sellLimit")
    val sellLimit: Int?,

    @Schema(example = "true")
    @JsonProperty(value = "stationBuys", required = true)
    val stationBuys: Boolean,

    @Schema(example = "500")
    @JsonProperty(value = "purchasePrice", required = true)
    val purchasePrice: Int?,

    @Schema(example = "10000")
    @JsonProperty(value = "purchaseLimit")
    val purchaseLimit: Int?
)
