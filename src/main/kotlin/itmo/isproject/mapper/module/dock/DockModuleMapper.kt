package itmo.isproject.mapper.module.dock

import itmo.isproject.dto.module.dock.DockModuleDto
import itmo.isproject.mapper.EntityMapper
import itmo.isproject.model.module.dock.DockModule
import org.mapstruct.Mapper

@Mapper(uses = [DockModuleBlueprintMapper::class])
interface DockModuleMapper : EntityMapper<DockModuleDto, DockModule>
