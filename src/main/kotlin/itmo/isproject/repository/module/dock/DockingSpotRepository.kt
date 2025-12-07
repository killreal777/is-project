package itmo.isproject.repository.module.dock

import itmo.isproject.model.module.dock.DockingSpot
import itmo.isproject.model.user.Spaceship
import itmo.isproject.model.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DockingSpotRepository : JpaRepository<DockingSpot, Int> {

    fun findAllByIsOccupiedTrue(pageable: Pageable): Page<DockingSpot>

    fun findFirstBySizeAndIsOccupiedFalse(size: Spaceship.Size): DockingSpot?

    fun findBySpaceshipPilot(pilot: User): DockingSpot?

    fun existsBySpaceship(spaceship: Spaceship): Boolean
}
