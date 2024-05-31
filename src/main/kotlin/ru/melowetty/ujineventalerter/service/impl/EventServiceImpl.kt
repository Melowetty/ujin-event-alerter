package ru.melowetty.ujineventalerter.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import ru.melowetty.ujineventalerter.dto.EventShortDto
import ru.melowetty.ujineventalerter.entity.Event
import ru.melowetty.ujineventalerter.model.EventType
import ru.melowetty.ujineventalerter.repository.CameraRepository
import ru.melowetty.ujineventalerter.repository.EventRepository
import ru.melowetty.ujineventalerter.service.EventService
import java.time.LocalDateTime

@Service
class EventServiceImpl(
    private val eventRepository: EventRepository,
    private val cameraRepository: CameraRepository,
    private val objectMapper: ObjectMapper
): EventService {
    override fun createEvent(eventType: EventType, cameraId: String, description: String, startDate: LocalDateTime, data: Any?) {
        val camera = cameraRepository.getCameraByExternalId(cameraId) ?:
            throw EntityNotFoundException("Камера с таким ID не найдена!")
        val event = Event(
            id = 0L,
            camera = camera,
            startDateTime = startDate,
            type = eventType,
            endDateTime = null,
            data = objectMapper.writeValueAsString(data),
            description = description,
        )
        eventRepository.save(event)
    }

    override fun getEventsByBuilding(buildingId: Long, limit: Long, isOnlyActiveEvents: Boolean): List<EventShortDto> {
        TODO("Not yet implemented")
    }

    override fun getEventsByFloor(floorId: Long, isOnlyActiveEvents: Boolean): List<EventShortDto> {
        val events = eventRepository.findAllByCamera_FloorId(floorId)
        if(isOnlyActiveEvents) {
            return events.filter { it.endDateTime != null }.map { toDto(it) }
        }
        return events.map { toDto(it) }
    }

    fun toDto(event: Event): EventShortDto {
        return EventShortDto(
            id = event.id,
            type = event.type,
            cameraId = event.camera.id,
            data = event.data?.let { objectMapper.readValue(it) },
            description = event.description,
            startTime = event.startDateTime,
            endTime = event.endDateTime
        )
    }
}