package ru.melowetty.ujineventalerter.service

import ru.melowetty.ujineventalerter.dto.EventShortDto

interface EventService {
    fun getEvents(limit: Long = 100, isOnlyActiveEvents: Boolean = false): List<EventShortDto>
}