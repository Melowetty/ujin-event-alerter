package ru.melowetty.ujineventalerter.service

import ru.melowetty.ujineventalerter.model.EventType
import ru.melowetty.ujineventalerter.model.UjinBuilding

interface UjinApiService {
    fun getBuildings(): List<UjinBuilding>
    fun createIncident(buildingId: Long, eventType: EventType, description: String): Long
    fun getIncidentClosed(topicId: Long): Boolean
}