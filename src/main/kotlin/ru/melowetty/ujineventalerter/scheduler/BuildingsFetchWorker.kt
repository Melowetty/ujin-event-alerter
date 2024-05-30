package ru.melowetty.ujineventalerter.scheduler

import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.melowetty.ujineventalerter.service.BuildingService
import ru.melowetty.ujineventalerter.service.FloorService
import ru.melowetty.ujineventalerter.service.UjinApiService
import java.nio.file.Path

@Component
class BuildingsFetchWorker(
    private val ujinApiService: UjinApiService,
    private val buildingService: BuildingService,
    private val floorService: FloorService,
) {
    @Transactional
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    fun fetchBuildings() {
        val actualBuildings = ujinApiService.getBuildings()
        val buildingsFromDb = buildingService.getBuildings()

        val candidatesForCreating = actualBuildings.filter { building ->
            buildingsFromDb.find { it.externalId == building.id } == null
        }

        val candidatesForDeleting = buildingsFromDb.filter { building ->
            actualBuildings.find { it.id == building.externalId } == null
        }

        candidatesForDeleting.forEach { buildingService.deleteBuilding(it.id) }

        candidatesForCreating.forEach {
            val building = buildingService.createBuilding(name = it.name, externalId = it.id, location = it.location)
            floorService.createFloor(buildingId = building.id, number = 0, isOutside = true, name = "Улица")
            for(i in 1..it.floor) {
                floorService.createFloor(buildingId = building.id, number = i)
            }
        }

    }
}