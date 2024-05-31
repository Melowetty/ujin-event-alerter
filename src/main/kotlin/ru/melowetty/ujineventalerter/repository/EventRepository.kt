package ru.melowetty.ujineventalerter.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import ru.melowetty.ujineventalerter.entity.Event

interface EventRepository: CrudRepository<Event, Long> {
    fun findAllByCamera_FloorId(floorId: Long): List<Event>
    fun findAllByCamera_Floor_BuildingId(buildingId: Long): List<Event>
}