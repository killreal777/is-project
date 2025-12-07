package itmo.isproject.mapper.trade

import itmo.isproject.dto.trade.TradeDto
import itmo.isproject.mapper.EntityMapper
import itmo.isproject.mapper.user.UserMapper
import itmo.isproject.model.trade.Trade
import org.mapstruct.Mapper

@Mapper(uses = [UserMapper::class, TradeItemMapper::class])
interface TradeMapper : EntityMapper<TradeDto, Trade>
