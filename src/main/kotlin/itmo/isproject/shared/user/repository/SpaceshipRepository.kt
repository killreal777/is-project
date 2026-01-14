package itmo.isproject.shared.user.repository

import itmo.isproject.shared.user.model.Spaceship
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SpaceshipRepository : JpaRepository<Spaceship, Int> {

    fun findByPilotId(pilotId: Int?): Spaceship?

    fun findByPilotUsernameInternal(username: String?): Spaceship?
}
