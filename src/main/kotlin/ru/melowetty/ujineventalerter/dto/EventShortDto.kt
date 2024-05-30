package ru.melowetty.ujineventalerter.dto

import ru.melowetty.ujineventalerter.model.EventType
import java.time.LocalDateTime

data class EventShortDto(
    val id: Long,
    val type: EventType,
    val data: Any,
    val cameraId: Long,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime?
)
