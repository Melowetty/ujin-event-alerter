package ru.melowetty.ujineventalerter.service

import ru.melowetty.ujineventalerter.model.UjinBuilding

interface UjinApiService {
    fun getBuildings(): List<UjinBuilding>
    fun createIncident()
}