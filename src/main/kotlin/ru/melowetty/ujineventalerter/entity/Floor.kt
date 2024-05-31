package ru.melowetty.ujineventalerter.entity

import jakarta.persistence.*
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.Type
import org.springframework.web.bind.annotation.Mapping

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
    @OneToMany(fetch = FetchType.LAZY)
    val cameras: List<Camera>
)
