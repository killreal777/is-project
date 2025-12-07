package itmo.isproject.service.module

import itmo.isproject.dto.module.dock.DockModuleDto
import itmo.isproject.dto.module.dock.DockingSpotDto
import itmo.isproject.mapper.module.dock.DockModuleMapper
import itmo.isproject.mapper.module.dock.DockingSpotMapper
import itmo.isproject.model.user.User
import itmo.isproject.repository.module.dock.DockModuleRepository
import itmo.isproject.repository.module.dock.DockingSpotRepository
import itmo.isproject.service.user.SpaceshipService
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DockModuleService(
    private val dockModuleRepository: DockModuleRepository,
    private val dockModuleMapper: DockModuleMapper,
    private val dockingSpotRepository: DockingSpotRepository,
    private val dockingSpotMapper: DockingSpotMapper,
    private val spaceshipService: SpaceshipService
) {

    fun getAllDockModules(pageable: Pageable): Page<DockModuleDto> {
        return dockModuleRepository.findAll(pageable).map { dockModuleMapper.toDto(it) }
    }

    fun getDockModuleById(id: Int): DockModuleDto {
        return dockModuleRepository.findByIdOrNull(id)?.let { dockModuleMapper.toDto(it) }
            ?: throw EntityNotFoundException("Dock module not found with id: $id")
    }

    fun getAllDockingSpots(pageable: Pageable): Page<DockingSpotDto> {
        return dockingSpotRepository.findAll(pageable).map { dockingSpotMapper.toDto(it) }
    }

    fun getAllOccupiedDockingSpots(pageable: Pageable): Page<DockingSpotDto> {
        return dockingSpotRepository.findAllByIsOccupiedTrue(pageable).map { dockingSpotMapper.toDto(it) }
    }

    fun getOccupiedDockingSpotByPilot(pilot: User): DockingSpotDto {
        return dockingSpotRepository.findBySpaceshipPilot(pilot)?.let { dockingSpotMapper.toDto(it) }
            ?: throw EntityNotFoundException("Spaceship is not docked")
    }

    @Transactional
    fun dock(user: User): DockingSpotDto {
        val spaceship = spaceshipService.findSpaceshipEntityByPilotId(user.id)
        if (dockingSpotRepository.existsBySpaceship(spaceship)) {
            throw IllegalStateException("Spaceship is already docked")
        }
        val dockingSpot = dockingSpotRepository.findFirstBySizeAndIsOccupiedFalse(spaceship.size!!)
            ?: throw IllegalStateException("No suitable free docking spot found")
        dockingSpot.spaceship = spaceship
        dockingSpot.isOccupied = true
        return dockingSpotMapper.toDto(dockingSpotRepository.save(dockingSpot))
    }

    @Transactional
    fun undock(user: User) {
        val dockingSpot = dockingSpotRepository.findBySpaceshipPilot(user)
            ?: throw IllegalStateException("Spaceship is not docked")
        dockingSpot.spaceship = null
        dockingSpot.isOccupied = false
        dockingSpotRepository.save(dockingSpot)
    }
}
