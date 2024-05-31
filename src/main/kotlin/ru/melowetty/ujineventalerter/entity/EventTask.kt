package ru.melowetty.ujineventalerter.entity

import jakarta.persistence.*

@Entity
data class EventTask(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long,
    @OneToOne
    val event: Event,
    @Column(unique = true)
    val ticketId: Long,
)
