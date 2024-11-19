package itmo.is.project.repository.spaceship;

import itmo.is.project.model.spaceship.Spaceship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceshipRepository extends JpaRepository<Spaceship, Integer> {
}
