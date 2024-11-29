package itmo.is.project.controller.module;

import itmo.is.project.dto.module.storage.StorageModuleBlueprintDto;
import itmo.is.project.dto.module.storage.StorageModuleDto;
import itmo.is.project.service.module.StorageModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/modules/storage")
public class StorageModuleRestController extends ModuleRestController<StorageModuleDto, StorageModuleBlueprintDto> {

    @Autowired
    public StorageModuleRestController(StorageModuleService storageModuleService) {
        super(storageModuleService);
    }
}
