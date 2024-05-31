package ru.melowetty.ujineventalerter.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.melowetty.ujineventalerter.entity.EventTask

@Repository
interface EventTaskRepository: CrudRepository<EventTask, Long> {
}