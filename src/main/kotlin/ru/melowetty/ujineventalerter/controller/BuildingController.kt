package ru.melowetty.ujineventalerter.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.melowetty.ujineventalerter.dto.BuildingDto
import ru.melowetty.ujineventalerter.dto.FloorShortDto
import ru.melowetty.ujineventalerter.service.BuildingService
import ru.melowetty.ujineventalerter.service.FloorService

@RequestMapping("/building")
@RestController
class BuildingController(
    private val buildingService: BuildingService,
    private val floorService: FloorService,
) {
    @GetMapping
    fun getBuildings(): List<BuildingDto> {
        return buildingService.getBuildings()
    }

    @GetMapping("{buildingId}/floors")
    fun getBuildingFloors(@PathVariable("buildingId") buildingId: Long): List<FloorShortDto> {
        return floorService.getFloorsByBuilding(buildingId)
    }
}