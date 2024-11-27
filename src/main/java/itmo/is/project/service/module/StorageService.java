package itmo.is.project.service.module;

import itmo.is.project.dto.resource.ResourceAmountDto;
import itmo.is.project.dto.resource.ResourceDto;
import itmo.is.project.mapper.resource.ResourceAmountMapper;
import itmo.is.project.mapper.resource.ResourceMapper;
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

    private final StorageModuleRepository storageModuleRepository;


    public Page<ResourceAmountDto> getAllStoredResources(Pageable pageable) {
        return storedResourceRepository.sumAmountGroupedByResource(pageable)
                .map(array -> new ResourceAmountDto(
                        new ResourceDto(
                                (Integer) array[0],  // id
                                (String) array[1]    // name
                        ),
                        ((Long) array[2]).intValue() // amount
                ));
    }

    public Page<ResourceAmountDto> getAllStoredResourcesByStorages(Pageable pageable) {
        return storedResourceRepository.findAll(pageable)
                .map(storedResource -> resourceAmountMapper.toDto(storedResource.getResourceAmount()));
    }

    public Page<ResourceAmountDto> getAllResourcesInStorage(Integer resourceId, Integer storageModuleId, Pageable pageable) {
        return null;
    }

    public void store(Integer resourceId, Integer amount) {

    }

    public void retrieve(Integer resourceId, Integer amount) {

    }
}
