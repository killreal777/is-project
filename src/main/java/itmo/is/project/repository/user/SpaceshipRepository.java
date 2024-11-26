package itmo.is.project.repository.user;

import itmo.is.project.model.user.Spaceship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpaceshipRepository extends JpaRepository<Spaceship, Integer> {
    Optional<Spaceship> findByPilotId(Integer pilotId);

    Optional<Spaceship> findByPilotUsername(String username);
}
