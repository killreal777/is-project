package itmo.is.project.service.module.storage;

import itmo.is.project.dto.ResourceDto;
import itmo.is.project.mapper.ResourceMapper;
import itmo.is.project.model.module.storage.StoredResource;
import itmo.is.project.repository.module.storage.StoredResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final StoredResourceRepository storedResourceRepository;
    private final ResourceMapper resourceMapper;

    public void getAllStoredResources() {
        Map<ResourceDto, Integer> resources = storedResourceRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        storedResource -> resourceMapper.toDto(storedResource.getResource()),
                        Collectors.summingInt(StoredResource::getAmount)
                ));


    }

    public void getStoredResources(Integer resourceId, Integer storageModuleId) {

    }

    public void store(Integer resourceId, Integer amount) {

    }

    public void retrieve(Integer resourceId, Integer amount) {

    }
}
