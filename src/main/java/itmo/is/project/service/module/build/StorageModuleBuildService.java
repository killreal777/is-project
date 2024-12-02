package itmo.is.project.service.module.build;

import itmo.is.project.dto.module.storage.StorageModuleBlueprintDto;
import itmo.is.project.dto.module.storage.StorageModuleDto;
import itmo.is.project.mapper.module.storage.StorageModuleBlueprintMapper;
import itmo.is.project.mapper.module.storage.StorageModuleMapper;
import itmo.is.project.model.module.storage.StorageModule;
import itmo.is.project.model.module.storage.StorageModuleBlueprint;
import itmo.is.project.repository.module.storage.StorageModuleBlueprintRepository;
import itmo.is.project.repository.module.storage.StorageModuleRepository;
import itmo.is.project.service.module.StorageModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageModuleBuildService  extends
        BuildService<StorageModule, StorageModuleBlueprint, StorageModuleDto, StorageModuleBlueprintDto> {

    @Autowired
    public StorageModuleBuildService(
            StorageModuleService storageModuleService,
            StorageModuleBlueprintRepository storageModuleBlueprintRepository,
            StorageModuleBlueprintMapper storageModuleBlueprintMapper,
            StorageModuleRepository storageModuleRepository,
            StorageModuleMapper storageModuleMapper
    ) {
        super(
                storageModuleService,
                storageModuleBlueprintRepository,
                storageModuleBlueprintMapper,
                storageModuleRepository,
                storageModuleMapper,
                StorageModule::new
        );
    }
}
