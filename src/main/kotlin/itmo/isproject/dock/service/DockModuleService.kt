package itmo.isproject.dock.service

import itmo.isproject.dock.dto.DockModuleDto
import itmo.isproject.dock.dto.DockingSpotDto
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.withLoggingContext
import itmo.isproject.dock.mapper.DockModuleMapper
import itmo.isproject.dock.mapper.DockingSpotMapper
import itmo.isproject.shared.user.model.User
import itmo.isproject.dock.repository.DockModuleRepository
import itmo.isproject.dock.repository.DockingSpotRepository
import itmo.isproject.shared.user.service.SpaceshipService
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}

@Service
class DockModuleService(
    private val dockModuleRepository: DockModuleRepository,
    private val dockModuleMapper: DockModuleMapper,
    private val dockingSpotRepository: DockingSpotRepository,
    private val dockingSpotMapper: DockingSpotMapper,
    private val spaceshipService: SpaceshipService
) {

    fun getAllDockModules(pageable: Pageable): Page<DockModuleDto> {
        withLoggingContext("page" to pageable.pageNumber.toString()) {
            logger.debug { "Fetching all dock modules" }
        }
        return dockModuleRepository.findAll(pageable).map { dockModuleMapper.toDto(it) }
    }

    fun getDockModuleById(id: Int): DockModuleDto {
        withLoggingContext("dockModuleId" to id.toString()) {
            logger.debug { "Fetching dock module by" }
        }
        return dockModuleRepository.findByIdOrNull(id)?.let { dockModuleMapper.toDto(it) }
            ?: throw EntityNotFoundException("Dock module not found with id: $id")
    }

    fun getAllDockingSpots(pageable: Pageable): Page<DockingSpotDto> {
        withLoggingContext("page" to pageable.pageNumber.toString()) {
            logger.debug { "Fetching all docking spots" }
        }
        return dockingSpotRepository.findAll(pageable).map { dockingSpotMapper.toDto(it) }
    }

    fun getAllOccupiedDockingSpots(pageable: Pageable): Page<DockingSpotDto> {
        withLoggingContext("page" to pageable.pageNumber.toString()) {
            logger.debug { "Fetching all occupied docking spots" }
        }
        return dockingSpotRepository.findAllByIsOccupiedTrue(pageable).map { dockingSpotMapper.toDto(it) }
    }

    fun getOccupiedDockingSpotByPilot(pilot: User): DockingSpotDto {
        withLoggingContext("pilotId" to pilot.id.toString(), "username" to pilot.usernameInternal) {
            logger.debug { "Fetching occupied docking spot by" }
        }
        return dockingSpotRepository.findBySpaceshipPilot(pilot)?.let { dockingSpotMapper.toDto(it) }
            ?: throw EntityNotFoundException("Spaceship is not docked")
    }

    @Transactional
    fun dock(user: User): DockingSpotDto {
        withLoggingContext("userId" to user.id.toString(), "username" to user.usernameInternal) {
            logger.info { "Docking spaceship for" }
        }
        val spaceship = spaceshipService.findSpaceshipEntityByPilotId(user.id)
        if (dockingSpotRepository.existsBySpaceship(spaceship)) {
            withLoggingContext("userId" to user.id.toString(), "spaceshipId" to spaceship.id.toString()) {
                logger.warn { "Spaceship is already docked" }
            }
            throw IllegalStateException("Spaceship is already docked")
        }
        val dockingSpot = dockingSpotRepository.findFirstBySizeAndIsOccupiedFalse(spaceship.size!!)
            ?: throw IllegalStateException("No suitable free docking spot found")
        dockingSpot.spaceship = spaceship
        dockingSpot.isOccupied = true
        withLoggingContext(
            "userId" to user.id.toString(),
            "dockingSpotId" to dockingSpot.id.toString(),
            "size" to spaceship.size.toString()
        ) {
            logger.info { "Spaceship docked" }
        }
        return dockingSpotMapper.toDto(dockingSpotRepository.save(dockingSpot))
    }

    @Transactional
    fun undock(user: User) {
        withLoggingContext("userId" to user.id.toString(), "username" to user.usernameInternal) {
            logger.info { "Undocking spaceship for" }
        }
        val dockingSpot = dockingSpotRepository.findBySpaceshipPilot(user)
            ?: throw IllegalStateException("Spaceship is not docked")
        dockingSpot.spaceship = null
        dockingSpot.isOccupied = false
        dockingSpotRepository.save(dockingSpot)
        withLoggingContext("userId" to user.id.toString()) {
            logger.info { "Spaceship undocked" }
        }
    }
}
