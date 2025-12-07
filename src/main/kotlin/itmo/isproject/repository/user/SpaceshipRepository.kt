package itmo.isproject.repository.user

import itmo.isproject.model.user.Spaceship
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SpaceshipRepository : JpaRepository<Spaceship, Int> {

    fun findByPilotId(pilotId: Int?): Spaceship?

    fun findByPilotUsernameInternal(username: String?): Spaceship?
}
