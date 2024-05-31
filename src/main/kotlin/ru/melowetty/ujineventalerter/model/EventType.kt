package ru.melowetty.ujineventalerter.model

enum class EventType(val eventId: String, val additionalFields: List<String> = listOf()) {
    FIRE("bcad095a-f2b0-4c20-a7f0-88ee5da703b1"),
    LOST_ITEM("d99a411f-d0a6-42c4-b320-3c2dd0aa0821"),
    EMERGENCY_CAR("65afbe3d-41b2-41d2-802a-dbd2a00db0ed", listOf("VehicleClass"));
}