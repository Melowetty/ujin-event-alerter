package ru.melowetty.ujineventalerter.service

import ru.melowetty.ujineventalerter.dto.BuildingDto

interface BuildingService {
    fun createBuilding(name: String, location: String): BuildingDto
    fun getBuildings(): List<BuildingDto>
    fun deleteBuilding(id: Long)
}