package itmo.isproject.shared.user.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.withLoggingContext
import itmo.isproject.shared.user.dto.spaceship.CreateSpaceshipRequest
import itmo.isproject.shared.user.dto.spaceship.SpaceshipDto
import itmo.isproject.shared.user.dto.spaceship.UpdateSpaceshipRequest
import itmo.isproject.shared.user.mapper.SpaceshipMapper
import itmo.isproject.shared.user.model.Spaceship
import itmo.isproject.shared.user.model.User
import itmo.isproject.shared.user.repository.SpaceshipRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}

@Service
class SpaceshipService(
    private val spaceshipRepository: SpaceshipRepository,
    private val spaceshipMapper: SpaceshipMapper
) {

    fun findAllSpaceships(pageable: Pageable): Page<SpaceshipDto> {
        withLoggingContext("page" to pageable.pageNumber.toString()) {
            logger.debug { "Fetching all spaceships" }
        }
        return spaceshipRepository.findAll(pageable).map { spaceshipMapper.toDto(it) }
    }

    fun findSpaceshipById(id: Int): SpaceshipDto {
        withLoggingContext("spaceshipId" to id.toString()) {
            logger.debug { "Fetching spaceship by id" }
        }
        return spaceshipRepository.findByIdOrNull(id)?.let { spaceshipMapper.toDto(it) }
            ?: throw EntityNotFoundException("Spaceship not found with id: $id")
    }

    fun findSpaceshipByPilotId(pilotId: Int): SpaceshipDto {
        withLoggingContext("pilotId" to pilotId.toString()) {
            logger.debug { "Fetching spaceship by pilot id" }
        }
        return spaceshipMapper.toDto(findSpaceshipEntityByPilotId(pilotId))
    }

    fun findSpaceshipEntityByPilotId(pilotId: Int?): Spaceship {
        return spaceshipRepository.findByPilotId(pilotId)
            ?: throw EntityNotFoundException("Spaceship not found with pilot id: $pilotId")
    }

    @Transactional
    fun createSpaceship(request: CreateSpaceshipRequest, owner: User): SpaceshipDto {
        withLoggingContext("userId" to owner.id.toString(), "username" to owner.usernameInternal) {
            logger.info { "Creating spaceship" }
        }
        spaceshipRepository.findByPilotId(owner.id)?.also { spaceship ->
            withLoggingContext("spaceshipId" to spaceship.id.toString(), "userId" to owner.id.toString()) {
                logger.warn { "Spaceship already exists" }
            }
            throw IllegalStateException("Spaceship already exists with id: ${spaceship.id}")
        }
        var spaceship = spaceshipMapper.toEntity(request)
        spaceship.pilot = owner
        spaceship = spaceshipRepository.save(spaceship)
        withLoggingContext("spaceshipId" to spaceship.id.toString(), "size" to spaceship.size.toString()) {
            logger.info { "Spaceship created" }
        }
        return spaceshipMapper.toDto(spaceship)
    }

    @Transactional
    fun updateSpaceship(request: UpdateSpaceshipRequest, owner: User): SpaceshipDto {
        withLoggingContext("userId" to owner.id.toString(), "newSize" to request.size.toString()) {
            logger.info { "Updating spaceship" }
        }
        var spaceship = findSpaceshipEntityByPilotId(owner.id)
        spaceship.size = request.size
        spaceship = spaceshipRepository.save(spaceship)
        withLoggingContext("spaceshipId" to spaceship.id.toString(), "size" to spaceship.size.toString()) {
            logger.info { "Spaceship updated" }
        }
        return spaceshipMapper.toDto(spaceship)
    }
}
