package ru.melowetty.ujineventalerter.service

import ru.melowetty.ujineventalerter.dto.BuildingDto

interface BuildingService {
    fun createBuilding(name: String, location: String, externalId: Long): BuildingDto
    fun getBuildingById(id: Long): BuildingDto
    fun getBuildings(): List<BuildingDto>
    fun deleteBuilding(id: Long)
}