package itmo.is.project.mapper.module.dock;

import itmo.is.project.dto.module.dock.DockModuleBlueprintDto;
import itmo.is.project.mapper.EntityMapper;
import itmo.is.project.model.module.dock.DockModuleBlueprint;
import org.mapstruct.Mapper;

@Mapper
public interface DockModuleBlueprintMapper extends EntityMapper<DockModuleBlueprintDto, DockModuleBlueprint> {
}
