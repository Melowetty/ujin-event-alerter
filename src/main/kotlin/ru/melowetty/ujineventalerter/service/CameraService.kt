package ru.melowetty.ujineventalerter.service

import ru.melowetty.ujineventalerter.dto.CameraShortDto
import ru.melowetty.ujineventalerter.model.AvailableCamera

interface CameraService {
    fun getAvailableForConnectionCameras(): List<AvailableCamera>
    fun getConnectedCameras(): List<CameraShortDto>
    fun getConnectedCamerasByFloor(buildingId: Long, floorNumber: Int): List<CameraShortDto>
    fun disconnectCamera(cameraId: Long)
    fun connectCamera(floorId: Long, externalId: String): CameraShortDto
}