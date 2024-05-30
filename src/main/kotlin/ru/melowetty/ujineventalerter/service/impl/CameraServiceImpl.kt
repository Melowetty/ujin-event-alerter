package ru.melowetty.ujineventalerter.service.impl

import org.springframework.stereotype.Service
import ru.melowetty.ujineventalerter.dto.CameraShortDto
import ru.melowetty.ujineventalerter.model.AvailableCamera
import ru.melowetty.ujineventalerter.service.CameraService

@Service
class CameraServiceImpl: CameraService {
    override fun getAvailableForConnectionCameras(limit: Int?): List<AvailableCamera> {
        TODO("Not yet implemented")
    }

    override fun getConnectedCamerasByFloor(floorId: Long): List<CameraShortDto> {
        TODO("Not yet implemented")
    }

    override fun disconnectCamera(cameraId: Long) {
        TODO("Not yet implemented")
    }

    override fun connectCamera(floorId: Long, externalId: String): CameraShortDto {
        TODO("Not yet implemented")
    }
}