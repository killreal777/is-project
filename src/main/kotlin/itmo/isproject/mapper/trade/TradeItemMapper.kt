package itmo.isproject.mapper.trade

import itmo.isproject.dto.trade.TradeItemDto
import itmo.isproject.mapper.EntityMapper
import itmo.isproject.model.trade.TradeItem
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface TradeItemMapper : EntityMapper<TradeItemDto, TradeItem> {

    @Mapping(source = "trade.id", target = "tradeId")
    override fun toDto(entity: TradeItem): TradeItemDto
}
