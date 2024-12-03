package itmo.is.project.controller.module;

import itmo.is.project.dto.module.BuildModuleRequest;
import itmo.is.project.dto.module.storage.StorageModuleBlueprintDto;
import itmo.is.project.dto.module.storage.StorageModuleDto;
import itmo.is.project.dto.resource.ResourceAmountDto;
import itmo.is.project.dto.resource.StoredResourceDto;
import itmo.is.project.model.resource.ResourceIdAmount;
import itmo.is.project.service.module.StorageModuleService;
import itmo.is.project.service.module.build.StorageModuleBuildService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.ok(storageModuleBuildService.buildModule(buildModuleRequest));
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

    @PutMapping("/resources/{resourceId}/store")
    public ResponseEntity<Void> store(@PathVariable Integer resourceId, @RequestBody Integer amount) {
        storageModuleService.storeById(new ResourceIdAmount(resourceId, amount));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/resources/store")
    public ResponseEntity<Void> storeAll(@RequestBody List<ResourceIdAmount> resources) {
        storageModuleService.storeAllById(resources);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/resources/{resourceId}/retrieve")
    public ResponseEntity<Void> retrieve(@PathVariable Integer resourceId, @RequestBody Integer amount) {
        storageModuleService.retrieveById(new ResourceIdAmount(resourceId, amount));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/resources/retrieve")
    public ResponseEntity<Void> retrieveAll(@RequestBody List<ResourceIdAmount> resources) {
        storageModuleService.retrieveAllById(resources);
        return ResponseEntity.ok().build();
    }
}
