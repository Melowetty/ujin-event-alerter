package ru.melowetty.ujineventalerter.controller.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class CameraConnectRequest(
    @Min(1)
    val floorId: Long,
    @NotBlank
    val externalId: String,
    val x: Float,
    val y: Float,
)