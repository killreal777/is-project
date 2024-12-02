package itmo.is.project.service.module;

import itmo.is.project.dto.resource.ResourceAmountDto;
import itmo.is.project.dto.resource.StoredResourceDto;
import itmo.is.project.mapper.resource.ResourceAmountMapper;
import itmo.is.project.mapper.resource.StoredResourceMapper;
import itmo.is.project.model.module.storage.StorageModuleFreeSpace;
import itmo.is.project.model.module.storage.StoredResource;
import itmo.is.project.model.resource.ResourceAmount;
import itmo.is.project.model.resource.ResourceAmountHolder;
import itmo.is.project.model.resource.ResourceIdAmountHolder;
import itmo.is.project.repository.module.storage.StorageModuleRepository;
import itmo.is.project.repository.module.storage.StoredResourceRepository;
import itmo.is.project.service.resource.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StorageModuleService {

    private final StoredResourceRepository storedResourceRepository;
    private final StorageModuleRepository storageModuleRepository;

    private final ResourceAmountMapper resourceAmountMapper;
    private final StoredResourceMapper storedResourceMapper;

    private final ResourceService resourceService;


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

    public int getTotalFreeSpace() {
        return storageModuleRepository.getTotalFreeSpaceInStorages();
    }


    @Transactional
    public void storeResourceById(ResourceIdAmountHolder resourceIdAmount) {
        storeResource(resourceService.toResourceAmount(resourceIdAmount));
    }

    @Transactional
    public void storeResource(ResourceAmountHolder resourceAmount) {
        storeAllResources(List.of(resourceAmount));
    }

    @Transactional
    public void storeAllResourcesById(Collection<? extends ResourceIdAmountHolder> resources) {
        storeAllResources(resources.stream().map(resourceService::toResourceAmount).toList());
    }

    @Transactional
    public void storeAllResources(Collection<? extends ResourceAmountHolder> resources) {
        int amountTotal = sumResourcesAmount(resources);
        checkFreeSpace(amountTotal);
        Deque<StorageModuleFreeSpace> storages = getAvailableStorages();
        for (ResourceAmountHolder resourceAmount : resources) {
            storeResourceToStorages(resourceAmount, storages);
        }
    }

    private Deque<StorageModuleFreeSpace> getAvailableStorages() {
        return new ArrayDeque<>(
                storageModuleRepository.findAllHavingFreeSpace().stream()
                        .map(StorageModuleFreeSpace::new)
                        .toList()
        );
    }

    private void storeResourceToStorages(
            ResourceAmountHolder resourceAmount,
            Deque<StorageModuleFreeSpace> storages
    ) {
        while (resourceAmount.getAmount() > 0) {
            StorageModuleFreeSpace storageModuleFreeSpace = storages.poll();
            storeResourceUpToStorageCapacity(resourceAmount, Objects.requireNonNull(storageModuleFreeSpace));
            if (storageModuleFreeSpace.getFreeSpace() > 0) {
                storages.offerFirst(storageModuleFreeSpace);
            }
        }
    }

    private void storeResourceUpToStorageCapacity(
            ResourceAmountHolder resourceAmountToStore,
            StorageModuleFreeSpace storageModuleFreeSpace
    ) {
        StoredResource.CompositeKey id = new StoredResource.CompositeKey(
                storageModuleFreeSpace.getStorageModuleId(), resourceAmountToStore.getResourceId()
        );
        StoredResource storedResource = storedResourceRepository.findById(id).orElseGet(() ->
                new StoredResource(id, storageModuleFreeSpace.getStorageModule(), resourceAmountToStore.getResource(), 0)
        );
        int storeAmount = Math.min(resourceAmountToStore.getAmount(), storageModuleFreeSpace.getFreeSpace());
        storedResource.add(storeAmount);
        storedResourceRepository.save(storedResource);
        resourceAmountToStore.sub(storeAmount);
        storageModuleFreeSpace.sub(storeAmount);
    }


    @Transactional
    public void retrieveResourceById(ResourceIdAmountHolder resourceIdAmount) {
        retrieveResource(resourceService.toResourceAmount(resourceIdAmount));
    }

    @Transactional
    public void retrieveResource(ResourceAmountHolder resourceAmount) {
        retrieveAllResources(List.of(resourceAmount));
    }

    @Transactional
    public void retrieveAllResourcesById(Collection<? extends ResourceIdAmountHolder> resources) {
        retrieveAllResources(resources.stream().map(resourceService::toResourceAmount).toList());
    }

    @Transactional
    public void retrieveAllResources(Collection<? extends ResourceAmountHolder> resources) {
        for (ResourceAmountHolder resourceAmount : resources) {
            checkExistenceRequiredResourceAmount(resourceAmount);
        }
        for (ResourceAmountHolder resourceAmount : resources) {
            retrieveResourceFromStorages(resourceAmount);
        }
    }

    private void checkExistenceRequiredResourceAmount(ResourceIdAmountHolder required) {
        Integer resourceId = required.getResourceId();
        ResourceAmount total = storedResourceRepository.findResourceAmountTotal(resourceId).orElseThrow();
        if (total.getAmount() < required.getAmount()) {
            throw new IllegalStateException();
        }
    }

    private void retrieveResourceFromStorages(ResourceIdAmountHolder resourceIdAmount) {
        Integer resourceId = resourceIdAmount.getResourceId();
        List<StoredResource> storedResources = storedResourceRepository.findAllByResourceId(resourceId);

        int remainingAmount = resourceIdAmount.getAmount();
        for (StoredResource storedResource : storedResources) {
            if (storedResource.getAmount() <= remainingAmount) {
                storedResourceRepository.delete(storedResource);
                remainingAmount -= storedResource.getAmount();
            } else {
                storedResource.setAmount(storedResource.getAmount() - remainingAmount);
                storedResourceRepository.save(storedResource);
                remainingAmount = 0;
            }
            if (remainingAmount == 0) {
                break;
            }
        }
    }


    @Transactional
    public void retrieveAndStoreAllById(
            Collection<? extends ResourceIdAmountHolder> retrieve,
            Collection<? extends ResourceIdAmountHolder> store
    ) {
        retrieveAndStoreAll(
                retrieve.stream().map(resourceService::toResourceAmount).toList(),
                store.stream().map(resourceService::toResourceAmount).toList()
        );
    }

    @Transactional
    public void retrieveAndStoreAll(
            Collection<? extends ResourceAmountHolder> retrieve,
            Collection<? extends ResourceAmountHolder> store
    ) {
        int requiredSpace = sumResourcesAmount(store) - sumResourcesAmount(retrieve);
        if (requiredSpace > 0) {
            checkFreeSpace(requiredSpace);
        }
        retrieveAllResources(retrieve);
        storeAllResources(store);
    }


    private int sumResourcesAmount(Collection<? extends ResourceIdAmountHolder> resources) {
        return resources.stream()
                .mapToInt(ResourceIdAmountHolder::getAmount)
                .sum();
    }

    private void checkFreeSpace(int amount) {
        int freeSpace = storageModuleRepository.getTotalFreeSpaceInStorages();
        if (amount > freeSpace) {
            throw new IllegalStateException();
        }
    }
}
