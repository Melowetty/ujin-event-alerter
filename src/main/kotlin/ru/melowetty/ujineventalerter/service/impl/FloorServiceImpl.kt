package ru.melowetty.ujineventalerter.service.impl

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import ru.melowetty.ujineventalerter.dto.FloorDto
import ru.melowetty.ujineventalerter.dto.FloorShortDto
import ru.melowetty.ujineventalerter.entity.Floor
import ru.melowetty.ujineventalerter.mapper.BuildingMapper
import ru.melowetty.ujineventalerter.repository.BuildingRepository
import ru.melowetty.ujineventalerter.repository.FloorRepository
import ru.melowetty.ujineventalerter.service.FloorService

@Service
class FloorServiceImpl(
    private val floorRepository: FloorRepository,
    private val buildingRepository: BuildingRepository,
    private val buildingMapper: BuildingMapper,
): FloorService {
    override fun getFloorsByBuilding(buildingId: Long): List<FloorShortDto> {
        return floorRepository.getAllByBuildingId(buildingId).map { toShortDto(it) }
    }

    override fun getFloor(floorId: Long): FloorDto {
        return toDto(floorRepository.findById(floorId).orElseThrow { throw EntityNotFoundException("Такой этаж не найден!") })
    }

    override fun createFloor(buildingId: Long, number: Int, name: String?): FloorDto {
        val building = buildingRepository.findById(buildingId).orElseThrow { throw EntityNotFoundException("Такое здание не найдено!") }
        val floor = Floor(
            id = 0L,
            name = name,
            number = number,
            building = building,
        )
        val created = floorRepository.save(floor)
        return toDto(created)
    }

    private fun toDto(floor: Floor): FloorDto {
        return FloorDto(
            id = floor.id,
            number = floor.number,
            building = buildingMapper.toDto(floor.building),
            name = floor.name,
        )
    }

    private fun toShortDto(floor: Floor): FloorShortDto {
        return FloorShortDto(
            id = floor.id,
            buildingId = floor.building.id,
            name = floor.name,
            number = floor.number,
        )
    }
}