package ru.melowetty.ujineventalerter.dto

data class FloorDto(
    val id: Long,
    val name: String?,
    val number: Int,
    val building: BuildingDto,
    val isOutside: Boolean,
    val planBase64: String,
    val cameras: List<CameraShortDto>
)
