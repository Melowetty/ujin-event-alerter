package ru.melowetty.ujineventalerter.service.impl.response

import com.fasterxml.jackson.annotation.JsonProperty

data class MacroscropEventsResponse(
    @JsonProperty("EventId")
    val eventId: String
)