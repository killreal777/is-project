package itmo.isproject.trade.mapper

import itmo.isproject.trade.dto.TradeItemDto
import itmo.isproject.shared.common.mapper.EntityMapper
import itmo.isproject.trade.model.TradeItem
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface TradeItemMapper : EntityMapper<TradeItemDto, TradeItem> {

    @Mapping(source = "trade.id", target = "tradeId")
    override fun toDto(entity: TradeItem): TradeItemDto
}
