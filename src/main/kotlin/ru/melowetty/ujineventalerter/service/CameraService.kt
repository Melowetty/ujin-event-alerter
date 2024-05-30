package ru.melowetty.ujineventalerter.service

import ru.melowetty.ujineventalerter.dto.CameraInfo
import ru.melowetty.ujineventalerter.dto.CameraShortDto
import ru.melowetty.ujineventalerter.model.AvailableCamera

interface CameraService {
    fun getAvailableForConnectionCameras(limit: Int?): List<AvailableCamera>
    fun getConnectedCamerasByFloor(floorId: Long): List<CameraShortDto>
    fun disconnectCamera(cameraId: Long)
    fun connectCamera(floorId: Long, externalId: String, x: Float, y: Float): CameraShortDto
    fun getCameraInfo(cameraId: Long): CameraInfo
}