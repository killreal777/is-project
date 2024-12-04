package itmo.is.project.mapper.trade;

import itmo.is.project.dto.trade.TradeItemDto;
import itmo.is.project.mapper.EntityMapper;
import itmo.is.project.model.trade.TradeItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TradeItemMapper extends EntityMapper<TradeItemDto, TradeItem> {

    @Override
    @Mapping(source = "trade.id", target = "tradeId")
    TradeItemDto toDto(TradeItem entity);
}
