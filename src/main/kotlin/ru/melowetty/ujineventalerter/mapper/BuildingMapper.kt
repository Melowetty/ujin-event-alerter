package ru.melowetty.ujineventalerter.mapper

import org.springframework.stereotype.Component
import ru.melowetty.ujineventalerter.dto.BuildingDto
import ru.melowetty.ujineventalerter.entity.Building

@Component
class BuildingMapper {
    fun toDto(building: Building): BuildingDto {
        return BuildingDto(
            id = building.id,
            name = building.name,
            location = building.location,
            externalId = building.externalId,
        )
    }
}