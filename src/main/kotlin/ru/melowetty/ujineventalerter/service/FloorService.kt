package ru.melowetty.ujineventalerter.service

import ru.melowetty.ujineventalerter.dto.FloorDto
import ru.melowetty.ujineventalerter.dto.FloorShortDto

interface FloorService {
    fun getFloorByNumber(buildingId: Long, number: Long): FloorShortDto
    fun createFloor(buildingId: Long, name: String, number: Long): FloorDto
    fun deleteFloor(id: Long)
}