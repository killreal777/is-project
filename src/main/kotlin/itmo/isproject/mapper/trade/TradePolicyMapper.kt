package itmo.isproject.mapper.trade

import itmo.isproject.dto.trade.policy.TradePolicyDto
import itmo.isproject.dto.trade.policy.UpdateTradePolicyRequest
import itmo.isproject.mapper.EntityMapper
import itmo.isproject.model.trade.TradePolicy
import org.mapstruct.Mapper

@Mapper
interface TradePolicyMapper : EntityMapper<TradePolicyDto, TradePolicy> {

    fun toEntity(request: UpdateTradePolicyRequest): TradePolicy
}
