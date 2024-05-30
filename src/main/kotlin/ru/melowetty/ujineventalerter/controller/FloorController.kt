package ru.melowetty.ujineventalerter.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.melowetty.ujineventalerter.dto.FloorDto
import ru.melowetty.ujineventalerter.service.FloorService

@RestController
@RequestMapping("floor")
class FloorController(
    private val floorService: FloorService
) {
    @GetMapping("/{floorId}")
    fun getFloor(@PathVariable floorId: Long): FloorDto {
        return floorService.getFloor(floorId = floorId)
    }
}
