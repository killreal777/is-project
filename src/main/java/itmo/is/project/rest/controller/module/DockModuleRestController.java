package itmo.is.project.rest.controller.module;

import itmo.is.project.dto.module.dock.DockModuleBlueprintDto;
import itmo.is.project.dto.module.dock.DockModuleDto;
import itmo.is.project.service.module.DockModuleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/modules/dock")
public class DockModuleRestController extends ModuleRestController<DockModuleDto, DockModuleBlueprintDto> {

    public DockModuleRestController(DockModuleService dockModuleService) {
        super(dockModuleService);
    }
}
