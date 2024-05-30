package ru.melowetty.ujineventalerter.service

import ru.melowetty.ujineventalerter.dto.EventShortDto

interface EventService {
    fun getEventsByBuilding(buildingId: Long, limit: Long = 100, isOnlyActiveEvents: Boolean = false): List<EventShortDto>
    fun getEventsByFloor(floorId: Long, isOnlyActiveEvents: Boolean = false): List<EventShortDto>
}