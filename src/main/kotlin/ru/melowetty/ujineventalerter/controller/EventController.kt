package ru.melowetty.ujineventalerter.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.melowetty.ujineventalerter.dto.EventShortDto
import ru.melowetty.ujineventalerter.service.EventService

@RestController
@RequestMapping("event")
class EventController(
    private val eventService: EventService
) {
    @GetMapping
    fun getEventsByFloor(@RequestParam floorId: Long, @RequestParam isOnlyActiveEvents: Boolean = true): List<EventShortDto> {
        return eventService.getEventsByFloor(floorId, isOnlyActiveEvents = true)
    }
}