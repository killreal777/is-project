package itmo.is.project.dto.trade;

import itmo.is.project.model.resource.Resource;

public record TradeOfferDto(
        Resource resource,
        Integer amount,
        Integer price
) {
}
