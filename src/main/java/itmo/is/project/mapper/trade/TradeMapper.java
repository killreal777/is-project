package itmo.is.project.mapper.trade;

import itmo.is.project.dto.trade.TradeDto;
import itmo.is.project.mapper.EntityMapper;
import itmo.is.project.model.trade.Trade;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TradeMapper extends EntityMapper<TradeDto, Trade> {
}
