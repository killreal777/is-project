package itmo.isproject.dock.mapper

import itmo.isproject.dock.dto.DockModuleBlueprintDto
import itmo.isproject.shared.common.mapper.EntityMapper
import itmo.isproject.dock.model.DockModuleBlueprint
import org.mapstruct.Mapper

@Mapper
interface DockModuleBlueprintMapper : EntityMapper<DockModuleBlueprintDto, DockModuleBlueprint>
