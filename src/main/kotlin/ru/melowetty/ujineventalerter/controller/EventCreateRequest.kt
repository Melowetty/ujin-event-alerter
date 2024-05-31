package ru.melowetty.ujineventalerter.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.melowetty.ujineventalerter.model.EventType
import ru.melowetty.ujineventalerter.service.EventService
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("internal/event")
class EventCreateRequest(
    private val eventService: EventService
) {
    @PostMapping
    fun addEvent(@RequestBody data: Map<String, Any>) {
        val type = EventType.entries.find { it.eventId == data["EventId"].toString()} ?: return
        val cameraId = data["ChannelId"].toString()
        val time = data["Timestamp"].toString()
        val startTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
        val description = data["EventDescription"].toString()
        val fields = type.additionalFields
        val additional = mutableMapOf<String, Any>()
        fields.forEach {
            additional[it] = data[it].toString()
        }
        eventService.createEvent(eventType = type, cameraId = cameraId, startDate = startTime, description = description, data = additional)
    }
}