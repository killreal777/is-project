package itmo.isproject.mapper.module.dock

import itmo.isproject.dto.module.dock.DockModuleBlueprintDto
import itmo.isproject.mapper.EntityMapper
import itmo.isproject.model.module.dock.DockModuleBlueprint
import org.mapstruct.Mapper

@Mapper
interface DockModuleBlueprintMapper : EntityMapper<DockModuleBlueprintDto, DockModuleBlueprint>
