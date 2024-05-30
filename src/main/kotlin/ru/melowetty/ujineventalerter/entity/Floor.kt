package ru.melowetty.ujineventalerter.entity

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
data class Floor(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long,
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    val building: Building,
    val name: String?,
    val number: Int,
    val isOutside: Boolean = false,
)
