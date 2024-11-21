package itmo.is.project.repository.module.storage;

import itmo.is.project.model.module.storage.StoredResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StoredResourceRepository extends JpaRepository<StoredResource, Integer> {

    @Query("""
            SELECT sr.resource.id AS id, sr.resource.name AS name, SUM(sr.amount) AS amount
            FROM StoredResource sr GROUP BY sr.resource.id, sr.resource.name
            """)
    Page<Object[]> sumAmountGroupedByResource(Pageable pageable);
}
