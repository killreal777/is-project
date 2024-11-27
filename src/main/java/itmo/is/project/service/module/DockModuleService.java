package itmo.is.project.service.module;

import itmo.is.project.dto.module.dock.DockModuleBlueprintDto;
import itmo.is.project.dto.module.dock.DockModuleDto;
import itmo.is.project.mapper.module.DockModuleBlueprintMapper;
import itmo.is.project.mapper.module.DockModuleMapper;
import itmo.is.project.model.module.dock.DockModule;
import itmo.is.project.model.module.dock.DockModuleBlueprint;
import itmo.is.project.repository.module.dock.DockModuleBlueprintRepository;
import itmo.is.project.repository.module.dock.DockModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DockModuleService extends
        ModuleService<DockModule, DockModuleBlueprint, DockModuleDto, DockModuleBlueprintDto> {

    @Autowired
    public DockModuleService(
            StorageService storageService,
            DockModuleBlueprintRepository dockModuleBlueprintRepository,
            DockModuleBlueprintMapper dockModuleBlueprintMapper,
            DockModuleRepository dockModuleRepository,
            DockModuleMapper dockModuleMapper
    ) {
        super(
                storageService,
                dockModuleBlueprintRepository,
                dockModuleBlueprintMapper,
                dockModuleRepository,
                dockModuleMapper,
                DockModule::new
        );
    }
}
