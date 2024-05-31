package ru.melowetty.ujineventalerter.scheduler

import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.melowetty.ujineventalerter.model.EventType
import ru.melowetty.ujineventalerter.service.MacroscopApiService
import ru.melowetty.ujineventalerter.service.UjinApiService

@Component
class EventsFetchWorker(
    private val ujinApiService: UjinApiService,
    private val macroscopApiService: MacroscopApiService,
) {
    @Async
    @Scheduled(fixedRate = 60 * 1000 * 1000)
    fun checkEvents() {
        EventType.values().forEach {
            macroscopApiService.getEvents(it.eventId)
        }
    }
}