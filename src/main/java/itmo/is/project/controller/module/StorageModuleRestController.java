package itmo.is.project.controller.module;

import itmo.is.project.dto.module.BuildModuleRequest;
import itmo.is.project.dto.module.storage.StorageModuleBlueprintDto;
import itmo.is.project.dto.module.storage.StorageModuleDto;
import itmo.is.project.dto.module.storage.StoredResourceDto;
import itmo.is.project.dto.resource.ResourceAmountDto;
import itmo.is.project.service.module.StorageModuleService;
import itmo.is.project.service.module.build.StorageModuleBuildService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/modules/storage")
@RequiredArgsConstructor
public class StorageModuleRestController {
    private final StorageModuleService storageModuleService;
    private final StorageModuleBuildService storageModuleBuildService;

    @GetMapping
    public ResponseEntity<Page<StorageModuleDto>> getAllStorageModules(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(storageModuleService.getAllStorageModules(pageable));
    }

    @GetMapping("/{storageModuleId}")
    public ResponseEntity<StorageModuleDto> getStorageModuleById(@PathVariable Integer storageModuleId) {
        return ResponseEntity.ok(storageModuleService.getStorageModuleById(storageModuleId));
    }

    @GetMapping("/build/blueprints")
    public ResponseEntity<Page<StorageModuleBlueprintDto>> findAllBlueprints(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(storageModuleBuildService.findAllBlueprints(pageable));
    }

    @PostMapping("/build")
    public ResponseEntity<StorageModuleDto> buildModule(@RequestBody BuildModuleRequest buildModuleRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(storageModuleBuildService.buildModule(buildModuleRequest));
    }

    @GetMapping("/resources")
    public ResponseEntity<Page<StoredResourceDto>> findAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(storageModuleService.getAllStoredResources(pageable));
    }

    @GetMapping("/resources/{storageId}")
    public ResponseEntity<Page<ResourceAmountDto>> findByStorageId(
            @PathVariable Integer storageId,
            @PageableDefault Pageable pageable
    ) {
        return ResponseEntity.ok(storageModuleService.getAllResourcesByStorageId(storageId, pageable));
    }

    @GetMapping("/resources/total")
    public ResponseEntity<Page<ResourceAmountDto>> findAllTotal(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(storageModuleService.getAllResourcesTotal(pageable));
    }

    @GetMapping("/resources/{resourceId}/total")
    public ResponseEntity<ResourceAmountDto> findByResourceId(@PathVariable Integer resourceId) {
        return ResponseEntity.ok(storageModuleService.getResourceAmountTotalByResourceId(resourceId));
    }
}
