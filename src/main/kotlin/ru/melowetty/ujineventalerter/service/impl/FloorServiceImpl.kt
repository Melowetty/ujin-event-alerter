package ru.melowetty.ujineventalerter.service.impl

import jakarta.persistence.EntityNotFoundException
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64
import org.springframework.stereotype.Service
import ru.melowetty.ujineventalerter.dto.CameraShortDto
import ru.melowetty.ujineventalerter.dto.FloorDto
import ru.melowetty.ujineventalerter.dto.FloorShortDto
import ru.melowetty.ujineventalerter.entity.Camera
import ru.melowetty.ujineventalerter.entity.Floor
import ru.melowetty.ujineventalerter.mapper.BuildingMapper
import ru.melowetty.ujineventalerter.repository.BuildingRepository
import ru.melowetty.ujineventalerter.repository.FloorRepository
import ru.melowetty.ujineventalerter.service.CameraService
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

    override fun createFloor(buildingId: Long, number: Int, name: String?, isOutside: Boolean): FloorDto {
        val building = buildingRepository.findById(buildingId).orElseThrow { throw EntityNotFoundException("Такое здание не найдено!") }
        val floor = Floor(
            id = 0L,
            name = name,
            number = number,
            building = building,
            isOutside = isOutside,
            cameras = listOf()
        )
        val created = floorRepository.save(floor)
        return toDto(created)
    }

    private fun toDto(floor: Floor): FloorDto {
        val streetPlan = "data:image/jpeg;base64," + Base64.toBase64String(this.javaClass.classLoader.getResourceAsStream("plans/street.jpg")!!.readAllBytes())
        val basePlan = "data:image/jpeg;base64," + Base64.toBase64String(this.javaClass.classLoader.getResourceAsStream("plans/base.jpg")!!.readAllBytes())
        return FloorDto(
            id = floor.id,
            number = floor.number,
            building = buildingMapper.toDto(floor.building),
            name = floor.name,
            isOutside = floor.isOutside,
            planBase64 = if(floor.isOutside) streetPlan else basePlan,
            cameras = floor.cameras.map { toShortDto(camera = it) }
        )
    }

    private fun toShortDto(floor: Floor): FloorShortDto {
        return FloorShortDto(
            id = floor.id,
            buildingId = floor.building.id,
            name = floor.name,
            number = floor.number,
            isOutside = floor.isOutside,
        )
    }

    private fun toShortDto(camera: Camera): CameraShortDto {
        return CameraShortDto(
            id = camera.id,
            x = camera.x,
            y = camera.y,
            name = camera.name,
        )
    }
}