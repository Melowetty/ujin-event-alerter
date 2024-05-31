package ru.melowetty.ujineventalerter.model

import java.time.Duration

enum class EventType(val eventId: String, val title: String, val additionalFields: List<String> = listOf()) {
    FIRE("bcad095a-f2b0-4c20-a7f0-88ee5da703b1", "Возгорание") {
        override fun getTypes(): List<Int> {
            return listOf(1590)
        }
                                                               },
    LOST_ITEM("d99a411f-d0a6-42c4-b320-3c2dd0aa0821", "Утерянный предмет"),
    EMERGENCY_CAR("65afbe3d-41b2-41d2-802a-dbd2a00db0ed", "Спец. транспорт", listOf("VehicleClass")) {
        override fun isLimitedEvent(): Boolean {
            return true;
        }

        override fun getEventLimit(): Duration {
            return Duration.ofMinutes(5)
        }
    };
    open fun isLimitedEvent(): Boolean {
        return false;
    }
    open fun getEventLimit(): Duration {
        return Duration.ofSeconds(0)
    }
    open fun getTypes(): List<Int> {
        return listOf()
    }
}