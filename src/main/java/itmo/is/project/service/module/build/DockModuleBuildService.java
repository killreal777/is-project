package itmo.is.project.service.module.build;

import itmo.is.project.dto.module.dock.DockModuleBlueprintDto;
import itmo.is.project.dto.module.dock.DockModuleDto;
import itmo.is.project.mapper.module.dock.DockModuleBlueprintMapper;
import itmo.is.project.mapper.module.dock.DockModuleMapper;
import itmo.is.project.model.module.dock.DockModule;
import itmo.is.project.model.module.dock.DockModuleBlueprint;
import itmo.is.project.repository.module.dock.DockModuleBlueprintRepository;
import itmo.is.project.repository.module.dock.DockModuleRepository;
import itmo.is.project.service.module.StorageModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DockModuleBuildService extends
        BuildService<DockModule, DockModuleBlueprint, DockModuleDto, DockModuleBlueprintDto> {

    @Autowired
    public DockModuleBuildService(
            StorageModuleService storageModuleService,
            DockModuleBlueprintRepository dockModuleBlueprintRepository,
            DockModuleBlueprintMapper dockModuleBlueprintMapper,
            DockModuleRepository dockModuleRepository,
            DockModuleMapper dockModuleMapper
    ) {
        super(
                storageModuleService,
                dockModuleBlueprintRepository,
                dockModuleBlueprintMapper,
                dockModuleRepository,
                dockModuleMapper,
                DockModule::new
        );
    }
}
