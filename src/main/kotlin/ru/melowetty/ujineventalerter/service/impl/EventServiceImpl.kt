package ru.melowetty.ujineventalerter.service.impl

import org.springframework.stereotype.Service
import ru.melowetty.ujineventalerter.dto.EventShortDto
import ru.melowetty.ujineventalerter.service.EventService

@Service
class EventServiceImpl: EventService {
    override fun getEventsByBuilding(buildingId: Long, limit: Long, isOnlyActiveEvents: Boolean): List<EventShortDto> {
        TODO("Not yet implemented")
    }

    override fun getEventsByFloor(floorId: Long, isOnlyActiveEvents: Boolean): List<EventShortDto> {
        TODO("Not yet implemented")
    }
}