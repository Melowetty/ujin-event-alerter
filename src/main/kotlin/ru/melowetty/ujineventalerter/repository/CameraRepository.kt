package ru.melowetty.ujineventalerter.repository

import org.springframework.data.repository.CrudRepository
import ru.melowetty.ujineventalerter.entity.Camera

interface CameraRepository: CrudRepository<Camera, Long>