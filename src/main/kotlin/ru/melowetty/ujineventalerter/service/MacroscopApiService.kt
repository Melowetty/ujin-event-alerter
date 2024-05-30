package ru.melowetty.ujineventalerter.service

import ru.melowetty.ujineventalerter.model.AvailableCamera

interface MacroscopApiService {
    fun getAvailableForConnectionCameras(): List<AvailableCamera>
    fun getImageFromCamera(id: String): String
}