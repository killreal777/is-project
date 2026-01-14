package itmo.isproject.dock.mapper

import itmo.isproject.dock.dto.DockModuleDto
import itmo.isproject.shared.common.mapper.EntityMapper
import itmo.isproject.dock.model.DockModule
import org.mapstruct.Mapper

@Mapper(uses = [DockModuleBlueprintMapper::class])
interface DockModuleMapper : EntityMapper<DockModuleDto, DockModule>
