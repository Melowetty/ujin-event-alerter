package ru.melowetty.ujineventalerter.repository

import org.springframework.data.repository.CrudRepository
import ru.melowetty.ujineventalerter.entity.Building

interface BuildingRepository: CrudRepository<Building, Long>