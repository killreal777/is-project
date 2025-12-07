package itmo.isproject.service.user

import itmo.isproject.dto.user.spaceship.CreateSpaceshipRequest
import itmo.isproject.dto.user.spaceship.SpaceshipDto
import itmo.isproject.dto.user.spaceship.UpdateSpaceshipRequest
import itmo.isproject.mapper.user.SpaceshipMapper
import itmo.isproject.model.user.Spaceship
import itmo.isproject.model.user.User
import itmo.isproject.repository.user.SpaceshipRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SpaceshipService(
    private val spaceshipRepository: SpaceshipRepository,
    private val spaceshipMapper: SpaceshipMapper
) {

    fun findAllSpaceships(pageable: Pageable): Page<SpaceshipDto> {
        return spaceshipRepository.findAll(pageable).map { spaceshipMapper.toDto(it) }
    }

    fun findSpaceshipById(id: Int): SpaceshipDto {
        return spaceshipRepository.findByIdOrNull(id)?.let { spaceshipMapper.toDto(it) }
            ?: throw EntityNotFoundException("Spaceship not found with id: $id")
    }

    fun findSpaceshipByPilotId(pilotId: Int): SpaceshipDto {
        return spaceshipMapper.toDto(findSpaceshipEntityByPilotId(pilotId))
    }

    fun findSpaceshipEntityByPilotId(pilotId: Int?): Spaceship {
        return spaceshipRepository.findByPilotId(pilotId)
            ?: throw EntityNotFoundException("Spaceship not found with pilot id: $pilotId")
    }

    @Transactional
    fun createSpaceship(request: CreateSpaceshipRequest, owner: User): SpaceshipDto {
        spaceshipRepository.findByPilotId(owner.id)?.also { spaceship ->
            throw IllegalStateException("Spaceship already exists with id: ${spaceship.id}")
        }
        var spaceship = spaceshipMapper.toEntity(request)
        spaceship.pilot = owner
        spaceship = spaceshipRepository.save(spaceship)
        return spaceshipMapper.toDto(spaceship)
    }

    @Transactional
    fun updateSpaceship(request: UpdateSpaceshipRequest, owner: User): SpaceshipDto {
        var spaceship = findSpaceshipEntityByPilotId(owner.id)
        spaceship.size = request.size
        spaceship = spaceshipRepository.save(spaceship)
        return spaceshipMapper.toDto(spaceship)
    }
}
