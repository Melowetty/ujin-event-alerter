package ru.melowetty.ujineventalerter.service.impl.response

import com.fasterxml.jackson.annotation.JsonProperty

data class MacroscopCamerasResponse(
    @JsonProperty("Id")
    val id: String,
    @JsonProperty("Name")
    val name: String,
    @JsonProperty("Disabled")
    val disabled: Boolean,
)
