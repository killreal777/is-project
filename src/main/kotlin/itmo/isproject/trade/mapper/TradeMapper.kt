package itmo.isproject.trade.mapper

import itmo.isproject.trade.dto.TradeDto
import itmo.isproject.shared.common.mapper.EntityMapper
import itmo.isproject.shared.user.mapper.UserMapper
import itmo.isproject.trade.model.Trade
import org.mapstruct.Mapper

@Mapper(uses = [UserMapper::class, TradeItemMapper::class])
interface TradeMapper : EntityMapper<TradeDto, Trade>
