package ru.melowetty.ujineventalerter.repository

import org.springframework.data.repository.CrudRepository
import ru.melowetty.ujineventalerter.entity.Camera

interface CameraRepository: CrudRepository<Camera, Long> {
    fun getCamerasByFloorId(floorId: Long): List<Camera>
    fun getCameraByExternalId(externalId: String): Camera?
}