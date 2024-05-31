package ru.melowetty.ujineventalerter.entity

import jakarta.persistence.*

@Entity
data class Building(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long,
    val name: String,
    val location: String,
    @Column(unique = true)
    val externalId: Long,
)
