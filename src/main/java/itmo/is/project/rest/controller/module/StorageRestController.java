package itmo.is.project.rest.controller.module;

import itmo.is.project.dto.ResourceAmountDto;
import itmo.is.project.service.module.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/storage")
@RequiredArgsConstructor
public class StorageRestController {
    private final StorageService storageService;

    @GetMapping("/resources")
    public ResponseEntity<Page<ResourceAmountDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(storageService.findAll(pageable));
    }

    @GetMapping("/resources/summary")
    public ResponseEntity<Page<ResourceAmountDto>> findAllSummary(Pageable pageable) {
        return ResponseEntity.ok(storageService.findAllSummary(pageable));
    }
}
