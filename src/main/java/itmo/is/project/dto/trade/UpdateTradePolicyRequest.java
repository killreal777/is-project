package itmo.is.project.dto.trade;

public record UpdateTradePolicyRequest(
        Boolean stationSells,
        Integer sellPrice,
        Integer sellLimit,
        Boolean stationBuys,
        Integer purchasePrice,
        Integer purchaseLimit
) {
}
