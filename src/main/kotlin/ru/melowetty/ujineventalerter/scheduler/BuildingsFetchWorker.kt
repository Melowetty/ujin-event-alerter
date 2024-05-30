package ru.melowetty.ujineventalerter.scheduler

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.melowetty.ujineventalerter.service.BuildingService
import ru.melowetty.ujineventalerter.service.UjinApiService

@Component
class BuildingsFetchWorker(
    private val ujinApiService: UjinApiService,
    private val buildingService: BuildingService
) {
    @Transactional
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    fun fetchBuildings() {
        val actualBuildings = ujinApiService.getBuildings()
        val buildingsFromDb = buildingService.getBuildings()

    }
}