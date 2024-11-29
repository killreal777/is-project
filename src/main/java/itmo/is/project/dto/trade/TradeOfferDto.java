package itmo.is.project.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import itmo.is.project.model.resource.Resource;

public record TradeOfferDto(

        @JsonProperty(value = "resource", required = true)
        Resource resource,

        @Schema(example = "200")
        @JsonProperty(value = "amount", required = true)
        Integer amount,

        @Schema(example = "1000")
        @JsonProperty(value = "price", required = true)
        Integer price
) {
}
