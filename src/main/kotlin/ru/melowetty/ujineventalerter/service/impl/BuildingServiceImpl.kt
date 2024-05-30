package ru.melowetty.ujineventalerter.service.impl

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import ru.melowetty.ujineventalerter.dto.BuildingDto
import ru.melowetty.ujineventalerter.entity.Building
import ru.melowetty.ujineventalerter.mapper.BuildingMapper
import ru.melowetty.ujineventalerter.repository.BuildingRepository
import ru.melowetty.ujineventalerter.service.BuildingService

@Service
class BuildingServiceImpl(
    private val buildingRepository: BuildingRepository,
    private val buildingMapper: BuildingMapper,
): BuildingService {
    override fun createBuilding(name: String, location: String, externalId: Long): BuildingDto {
        val build = Building(
            id = 0L,
            externalId = externalId,
            name = name,
            location = location,
        )
        return buildingMapper.toDto(buildingRepository.save(build))
    }

    override fun getBuildingById(id: Long): BuildingDto {
        return buildingMapper.toDto(
            buildingRepository.findById(id).orElseThrow { throw EntityNotFoundException("Такое здание не найдено!") }
        )
    }

    override fun getBuildings(): List<BuildingDto> {
        return buildingRepository.findAll().map { buildingMapper.toDto(it) }
    }

    override fun deleteBuilding(id: Long) {
        return buildingRepository.deleteById(id)
    }
}