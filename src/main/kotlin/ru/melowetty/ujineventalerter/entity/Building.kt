package ru.melowetty.ujineventalerter.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Building(
    @Id
    val id: Long,
    val name: String,
    val location: String,
    val externalId: Long,
)
