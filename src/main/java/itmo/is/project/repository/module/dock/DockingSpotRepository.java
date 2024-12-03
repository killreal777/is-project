package itmo.is.project.repository.module.dock;

import itmo.is.project.model.module.dock.DockingSpot;
import itmo.is.project.model.user.Spaceship;
import itmo.is.project.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DockingSpotRepository extends JpaRepository<DockingSpot, Integer> {
    Page<DockingSpot> findAllByIsOccupiedTrue(Pageable pageable);

    Optional<DockingSpot> findFirstBySizeAndIsOccupiedFalse(Spaceship.Size size);

    Optional<DockingSpot> findBySpaceshipPilot(User pilot);

    boolean existsBySpaceship(Spaceship spaceship);
}
