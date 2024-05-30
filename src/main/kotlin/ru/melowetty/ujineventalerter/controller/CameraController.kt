package ru.melowetty.ujineventalerter.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import ru.melowetty.ujineventalerter.controller.request.CameraConnectRequest
import ru.melowetty.ujineventalerter.dto.CameraInfo
import ru.melowetty.ujineventalerter.dto.CameraShortDto
import ru.melowetty.ujineventalerter.model.AvailableCamera
import ru.melowetty.ujineventalerter.service.CameraService

@RestController
@RequestMapping("/camera")
class CameraController(
    private val cameraService: CameraService
) {
    @GetMapping("available")
    fun getAvailableForConnectionCameras(@RequestParam limit: Int?): List<AvailableCamera> {
        return cameraService.getAvailableForConnectionCameras(limit = limit)
    }

    @GetMapping
    fun getCameras(floorId: Long): List<CameraShortDto> {
        return cameraService.getConnectedCamerasByFloor(floorId)
    }

    @PostMapping
    fun connectCamera(@Valid @RequestBody cameraConnectRequest: CameraConnectRequest): CameraShortDto {
        return cameraService.connectCamera(
            floorId = cameraConnectRequest.floorId,
            externalId = cameraConnectRequest.externalId,
            x = cameraConnectRequest.x,
            y = cameraConnectRequest.y
        )
    }

    @DeleteMapping("{cameraId}")
    fun disconnectCamera(@PathVariable cameraId: Long) {
        cameraService.disconnectCamera(cameraId)
    }

    @GetMapping("{cameraId}")
    fun getCameraInfo(@PathVariable cameraId: Long): CameraInfo {
        return cameraService.getCameraInfo(cameraId)
    }
}