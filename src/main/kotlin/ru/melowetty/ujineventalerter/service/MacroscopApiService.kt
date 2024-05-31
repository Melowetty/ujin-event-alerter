package ru.melowetty.ujineventalerter.service

import ru.melowetty.ujineventalerter.model.AvailableCamera
import ru.melowetty.ujineventalerter.service.impl.response.MacroscropEventsResponse

interface MacroscopApiService {
    fun getAvailableForConnectionCameras(): List<AvailableCamera>
    fun getImageFromCamera(id: String): String
    fun getEvents(eventId: String): MacroscropEventsResponse
}