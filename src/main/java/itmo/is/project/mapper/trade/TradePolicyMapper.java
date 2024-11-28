package itmo.is.project.mapper.trade;

import itmo.is.project.dto.trade.TradePolicyDto;
import itmo.is.project.dto.trade.UpdateTradePolicyRequest;
import itmo.is.project.mapper.EntityMapper;
import itmo.is.project.model.trade.TradePolicy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TradePolicyMapper extends EntityMapper<TradePolicyDto, TradePolicy> {
    TradePolicy toEntity(UpdateTradePolicyRequest request);
}
