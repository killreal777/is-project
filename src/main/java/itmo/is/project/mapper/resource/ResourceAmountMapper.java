package itmo.is.project.mapper.resource;

import itmo.is.project.dto.ResourceAmountDto;
import itmo.is.project.mapper.EntityMapper;
import itmo.is.project.model.resource.ResourceAmount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ResourceMapper.class})
public interface ResourceAmountMapper extends EntityMapper<ResourceAmountDto, ResourceAmount> {
}
