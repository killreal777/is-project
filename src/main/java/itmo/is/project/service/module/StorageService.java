package itmo.is.project.service.module;

import itmo.is.project.dto.resource.ResourceAmountDto;
import itmo.is.project.dto.resource.StoredResourceDto;
import itmo.is.project.mapper.resource.ResourceAmountMapper;
import itmo.is.project.mapper.resource.ResourceMapper;
import itmo.is.project.mapper.resource.StoredResourceMapper;
import itmo.is.project.repository.module.storage.StorageModuleRepository;
import itmo.is.project.repository.module.storage.StoredResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final StoredResourceRepository storedResourceRepository;
    private final ResourceMapper resourceMapper;
    private final ResourceAmountMapper resourceAmountMapper;
    private final StoredResourceMapper storedResourceMapper;

    private final StorageModuleRepository storageModuleRepository;

    public Page<StoredResourceDto> getAllStoredResources(Pageable pageable) {
        return storedResourceRepository.findAll(pageable)
                .map(storedResourceMapper::toDto);
    }

    public Page<ResourceAmountDto> getAllResourceAmountsTotal(Pageable pageable) {
        return storedResourceRepository.findAllResourceAmountsTotal(pageable)
                .map(resourceAmountMapper::toDto);
    }

    public ResourceAmountDto getResourceAmountTotal(Integer resourceId) {
        return storedResourceRepository.findResourceAmountTotal(resourceId)
                .map(resourceAmountMapper::toDto).orElseThrow();
    }

    public Page<ResourceAmountDto> getAllResourceAmountsByStorageId(Integer storageModuleId, Pageable pageable) {
        return storedResourceRepository.findAllByStorageId(storageModuleId, pageable)
                .map(storedResource -> resourceAmountMapper.toDto(storedResource.getResourceAmount()));
    }


    public void store(Integer resourceId, Integer amount) {

    }

    public void retrieve(Integer resourceId, Integer amount) {

    }
}
