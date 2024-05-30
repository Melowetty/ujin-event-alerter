package ru.melowetty.ujineventalerter.dto

data class FloorDto(
    val id: Long,
    val name: String?,
    val number: Int,
    val building: BuildingDto,
)
