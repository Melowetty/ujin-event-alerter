package ru.melowetty.ujineventalerter.entity

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import ru.melowetty.ujineventalerter.model.EventType
import java.time.LocalDateTime

@Entity
data class Event(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long,
    val externalId: Long,
    @ManyToOne(cascade = [CascadeType.REMOVE])
    @OnDelete(action = OnDeleteAction.CASCADE)
    val camera: Camera,
    @Enumerated(value = EnumType.STRING)
    val type: EventType,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime?
)
