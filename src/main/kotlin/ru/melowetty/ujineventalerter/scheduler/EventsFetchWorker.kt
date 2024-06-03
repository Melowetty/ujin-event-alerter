package ru.melowetty.ujineventalerter.scheduler

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.melowetty.ujineventalerter.repository.EventRepository
import ru.melowetty.ujineventalerter.repository.EventTaskRepository
import ru.melowetty.ujineventalerter.service.UjinApiService
import java.time.LocalDateTime

@Component
class EventsFetchWorker(
    private val ujinApiService: UjinApiService,
    private val eventTaskRepository: EventTaskRepository,
    private val eventRepository: EventRepository,
) {
    @Scheduled(fixedRate = 30 * 1000)
    fun checkEvents() {
        eventTaskRepository.findAll().filter { ujinApiService.getIncidentClosed(it.ticketId) }.forEach {
            eventRepository.save(it.event.copy(endDateTime = LocalDateTime.now()))
            eventTaskRepository.delete(it)
        }
    }
}