package ru.melowetty.ujineventalerter.dto

data class FloorShortDto(
    val id: Long,
    val buildingId: Long,
    val number: Int,
    val name: String?,
    val isOutside: Boolean,
)
