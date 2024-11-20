package itmo.is.project.repository.module.storage;

import itmo.is.project.model.resource.ResourceAmount;
import itmo.is.project.model.module.storage.StoredResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StoredResourceRepository extends JpaRepository<StoredResource, Integer> {

    @Query("""
            SELECT sr.resource.id AS resourceId, SUM(sr.amount) AS totalAmount
            FROM StoredResource sr
            GROUP BY sr.resource.id
            """)
    Page<ResourceAmount> findTotalResourceAmounts(Pageable pageable);

    @Query("SELECT sr FROM StoredResource sr WHERE sr.storage.id = storageModuleId")
    Page<StoredResource> findStoredResourceByStorageId(Integer storageModuleId, Pageable pageable);

    Page<StoredResource> findStoredResourcesByResourceId(Integer resourceId, Pageable pageable);
}
