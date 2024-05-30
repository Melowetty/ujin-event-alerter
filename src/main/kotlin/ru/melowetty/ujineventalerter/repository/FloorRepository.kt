package ru.melowetty.ujineventalerter.repository

import org.springframework.data.repository.CrudRepository
import ru.melowetty.ujineventalerter.entity.Floor

interface FloorRepository: CrudRepository<Floor, Long> {
    fun getAllByBuildingId(buildingId: Long): List<Floor>
}