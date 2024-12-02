package itmo.is.project.controller.module;

import itmo.is.project.dto.module.BuildModuleRequest;
import itmo.is.project.dto.module.storage.StorageModuleBlueprintDto;
import itmo.is.project.dto.module.storage.StorageModuleDto;
import itmo.is.project.dto.resource.ResourceAmountDto;
import itmo.is.project.dto.resource.StoredResourceDto;
import itmo.is.project.model.resource.ResourceIdAmount;
import itmo.is.project.service.module.StorageService;
import itmo.is.project.service.module.build.StorageModuleBuildService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/modules/storage")
@RequiredArgsConstructor
public class StorageRestController {
    private final StorageService storageService;
    private final StorageModuleBuildService storageModuleBuildService;

    @GetMapping("/build/blueprints")
    public ResponseEntity<Page<StorageModuleBlueprintDto>> findAllBlueprints(Pageable pageable) {
        return ResponseEntity.ok(storageModuleBuildService.findAllBlueprints(pageable));
    }

    @PostMapping("/build")
    public ResponseEntity<StorageModuleDto> buildModule(
            @RequestBody BuildModuleRequest buildModuleRequest
    ) {
        return ResponseEntity.ok(storageModuleBuildService.buildModule(buildModuleRequest));
    }

    @GetMapping("/resources")
    public ResponseEntity<Page<StoredResourceDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(storageService.getAllStoredResources(pageable));
    }

    @GetMapping("/resources/{storageId}")
    public ResponseEntity<Page<ResourceAmountDto>> findByStorageId(@PathVariable Integer storageId, Pageable pageable) {
        return ResponseEntity.ok(storageService.getAllResourceAmountsByStorageId(storageId, pageable));
    }

    @GetMapping("/resources/total")
    public ResponseEntity<Page<ResourceAmountDto>> findAllTotal(Pageable pageable) {
        return ResponseEntity.ok(storageService.getAllResourceAmountsTotal(pageable));
    }

    @GetMapping("/resources/{resourceId}/total")
    public ResponseEntity<ResourceAmountDto> findByResourceId(@PathVariable Integer resourceId) {
        return ResponseEntity.ok(storageService.getResourceAmountTotal(resourceId));
    }

    @PutMapping("/resources/{resourceId}/store")
    public ResponseEntity<Void> store(
            @PathVariable Integer resourceId,
            @RequestBody Integer amount
    ) {
        storageService.storeById(new ResourceIdAmount(resourceId, amount));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/resources/store")
    public ResponseEntity<Void> storeAll(
            @RequestBody List<ResourceIdAmount> resources
    ) {
        storageService.storeAll(resources);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/resources/{resourceId}/retrieve")
    public ResponseEntity<Void> retrieve(
            @PathVariable Integer resourceId,
            @RequestBody Integer amount
    ) {
        storageService.retrieve(new ResourceIdAmount(resourceId, amount));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/resources/retrieve")
    public ResponseEntity<Void> retrieveAll(
            @RequestBody List<ResourceIdAmount> resources
    ) {
        storageService.retrieveAllById(resources);
        return ResponseEntity.ok().build();
    }
}
