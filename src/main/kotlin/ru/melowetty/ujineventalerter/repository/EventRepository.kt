package ru.melowetty.ujineventalerter.repository

import org.springframework.data.repository.CrudRepository
import ru.melowetty.ujineventalerter.entity.Event

interface EventRepository: CrudRepository<Event, Long>