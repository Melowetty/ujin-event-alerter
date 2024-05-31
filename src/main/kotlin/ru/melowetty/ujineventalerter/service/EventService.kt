package ru.melowetty.ujineventalerter.service

import ru.melowetty.ujineventalerter.dto.EventShortDto
import ru.melowetty.ujineventalerter.model.EventType
import java.time.LocalDateTime

interface EventService {
    fun getEventsByBuilding(buildingId: Long, limit: Long = 100, isOnlyActiveEvents: Boolean = false): List<EventShortDto>
    fun getEventsByFloor(floorId: Long, isOnlyActiveEvents: Boolean = false): List<EventShortDto>
    fun createEvent(eventType: EventType, cameraId: String, description: String, startDate: LocalDateTime, data: Any?)
}