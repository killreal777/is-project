package itmo.is.project.service.module;

import itmo.is.project.dto.module.storage.StorageModuleBlueprintDto;
import itmo.is.project.dto.module.storage.StorageModuleDto;
import itmo.is.project.mapper.module.StorageModuleBlueprintMapper;
import itmo.is.project.mapper.module.StorageModuleMapper;
import itmo.is.project.model.module.storage.StorageModule;
import itmo.is.project.model.module.storage.StorageModuleBlueprint;
import itmo.is.project.repository.module.storage.StorageModuleBlueprintRepository;
import itmo.is.project.repository.module.storage.StorageModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageModuleService extends
        ModuleService<StorageModule, StorageModuleBlueprint, StorageModuleDto, StorageModuleBlueprintDto> {

    @Autowired
    public StorageModuleService(
            StorageService storageService,
            StorageModuleBlueprintRepository storageModuleBlueprintRepository,
            StorageModuleBlueprintMapper storageModuleBlueprintMapper,
            StorageModuleRepository storageModuleRepository,
            StorageModuleMapper storageModuleMapper
    ) {
        super(
                storageService,
                storageModuleBlueprintRepository,
                storageModuleBlueprintMapper,
                storageModuleRepository,
                storageModuleMapper,
                StorageModule::new
        );
    }
}
