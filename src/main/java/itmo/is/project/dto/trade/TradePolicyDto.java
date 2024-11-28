package itmo.is.project.dto.trade;

import itmo.is.project.model.resource.Resource;

public record TradePolicyDto(
        Integer resourceId,
        Resource resource,
        Boolean stationSells,
        Integer sellPrice,
        Integer sellLimit,
        Boolean stationBuys,
        Integer purchasePrice,
        Integer purchaseLimit
) {
}
