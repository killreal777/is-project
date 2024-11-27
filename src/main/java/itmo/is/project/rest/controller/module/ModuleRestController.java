package itmo.is.project.rest.controller.module;

import itmo.is.project.dto.module.BuildModuleRequest;
import itmo.is.project.service.module.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
public abstract class ModuleRestController<ModuleDto, BlueprintDto> {
    protected final ModuleService<?, ?, ModuleDto, BlueprintDto> moduleService;

    @GetMapping
    public ResponseEntity<Page<ModuleDto>> findAllModules(Pageable pageable) {
        return ResponseEntity.ok(moduleService.findAllModules(pageable));
    }

    @GetMapping("/build/blueprints")
    public ResponseEntity<Page<BlueprintDto>> findAllBlueprints(Pageable pageable) {
        return ResponseEntity.ok(moduleService.findAllBlueprints(pageable));
    }

    @PostMapping("/build")
    public ResponseEntity<ModuleDto> buildModule(
            @RequestBody BuildModuleRequest buildModuleRequest
    ) {
        return ResponseEntity.ok(moduleService.buildModule(buildModuleRequest));
    }
}
