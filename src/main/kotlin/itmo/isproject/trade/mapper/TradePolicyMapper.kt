package itmo.isproject.trade.mapper

import itmo.isproject.trade.dto.policy.TradePolicyDto
import itmo.isproject.trade.dto.policy.UpdateTradePolicyRequest
import itmo.isproject.shared.common.mapper.EntityMapper
import itmo.isproject.trade.model.TradePolicy
import org.mapstruct.Mapper

@Mapper
interface TradePolicyMapper : EntityMapper<TradePolicyDto, TradePolicy> {

    fun toEntity(request: UpdateTradePolicyRequest): TradePolicy
}
