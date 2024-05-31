package ru.melowetty.ujineventalerter.entity

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
data class Camera(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long,
    @Column(unique = true)
    val externalId: String,
    val name: String,
    val x: Float,
    val y: Float,
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    val floor: Floor
)
