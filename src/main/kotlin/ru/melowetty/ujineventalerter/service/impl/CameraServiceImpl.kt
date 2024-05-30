package ru.melowetty.ujineventalerter.service.impl

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import ru.melowetty.ujineventalerter.dto.CameraInfo
import ru.melowetty.ujineventalerter.dto.CameraShortDto
import ru.melowetty.ujineventalerter.entity.Camera
import ru.melowetty.ujineventalerter.model.AvailableCamera
import ru.melowetty.ujineventalerter.repository.CameraRepository
import ru.melowetty.ujineventalerter.repository.FloorRepository
import ru.melowetty.ujineventalerter.service.CameraService
import ru.melowetty.ujineventalerter.service.MacroscopApiService

@Service
class CameraServiceImpl(
    private val cameraRepository: CameraRepository,
    private val floorRepository: FloorRepository,
    private val macroscopApiService: MacroscopApiService,
): CameraService {
    override fun getAvailableForConnectionCameras(limit: Int?): List<AvailableCamera> {
        val connectedCameras = cameraRepository.findAll()
        val camerasInSystem = macroscopApiService.getAvailableForConnectionCameras()
        val availableCameras = camerasInSystem.filter { camera ->
            connectedCameras.find { it.externalId == camera.id} == null
        }
        return if(limit != null) availableCameras.take(limit)
        else availableCameras
    }

    override fun getConnectedCamerasByFloor(floorId: Long): List<CameraShortDto> {
        return cameraRepository.getCamerasByFloorId(floorId).map { toShortDto(it) }
    }

    override fun disconnectCamera(cameraId: Long) {
        cameraRepository.deleteById(cameraId)
    }

    override fun connectCamera(floorId: Long, externalId: String, x: Float, y: Float): CameraShortDto {
        val externalCamera = macroscopApiService.getAvailableForConnectionCameras().find { it.id == externalId }
            ?: throw EntityNotFoundException("Камера с таким ID не найдена!")
        val floor = floorRepository.findById(floorId).orElseThrow { throw EntityNotFoundException("Этаж с таким ID не найден!") }
        val camera = Camera(
            id = 0L,
            externalId = externalId,
            x = x,
            y = y,
            floor = floor,
            name = externalCamera.name
        )
        return toShortDto(cameraRepository.save(camera))
    }

    override fun getCameraInfo(cameraId: Long): CameraInfo {
        val camera = cameraRepository.findById(cameraId)
            .orElseThrow { throw EntityNotFoundException("Камера с таким ID не найдена!") }
        return CameraInfo(
            name = camera.name,
            image = macroscopApiService.getImageFromCamera(camera.externalId)
        )
    }

    private fun toShortDto(camera: Camera): CameraShortDto {
        return CameraShortDto(
            id = camera.id,
            x = camera.x,
            y = camera.y,
            name = camera.name,
        )
    }
}