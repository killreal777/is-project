package itmo.is.project.rest.controller.module;

import itmo.is.project.dto.resource.ResourceAmountDto;
import itmo.is.project.dto.resource.StoredResourceDto;
import itmo.is.project.service.module.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/storage")
@RequiredArgsConstructor
public class StorageRestController {
    private final StorageService storageService;

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

    @GetMapping("/resources/total/{resourceId}")
    public ResponseEntity<ResourceAmountDto> findByResourceId(@PathVariable Integer resourceId) {
        return ResponseEntity.ok(storageService.getResourceAmountTotal(resourceId));
    }
}
