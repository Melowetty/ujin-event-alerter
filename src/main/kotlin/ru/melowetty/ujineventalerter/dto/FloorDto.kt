package ru.melowetty.ujineventalerter.dto

data class FloorDto(
    val id: Long,
    val name: String,
    val number: Long,
    val building: BuildingDto,
)
