package ru.melowetty.ujineventalerter.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
data class Floor(
    @Id
    val id: Long,
    @ManyToOne(cascade = [CascadeType.REMOVE])
    @OnDelete(action = OnDeleteAction.CASCADE)
    val building: Building,
    val name: String,
    val number: Int,
)
