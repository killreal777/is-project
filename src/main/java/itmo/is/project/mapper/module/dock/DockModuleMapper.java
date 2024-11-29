package itmo.is.project.mapper.module.dock;

import itmo.is.project.dto.module.dock.DockModuleDto;
import itmo.is.project.mapper.EntityMapper;
import itmo.is.project.model.module.dock.DockModule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DockModuleBlueprintMapper.class})
public interface DockModuleMapper extends EntityMapper<DockModuleDto, DockModule> {
}
