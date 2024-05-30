package ru.melowetty.ujineventalerter.service

import ru.melowetty.ujineventalerter.dto.FloorDto
import ru.melowetty.ujineventalerter.dto.FloorShortDto

interface FloorService {
    fun getFloorsByBuilding(buildingId: Long): List<FloorShortDto>
    fun getFloor(floorId: Long): FloorDto
    fun createFloor(buildingId: Long, number: Int, name: String? = null): FloorDto
}