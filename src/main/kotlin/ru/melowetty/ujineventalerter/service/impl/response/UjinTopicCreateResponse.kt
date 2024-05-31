package ru.melowetty.ujineventalerter.service.impl.response

data class UjinTopicCreateResponse(
    val error: Int,
    val data: UjinTopicDataResponse
)

data class UjinTopicDataResponse(
    val ticket: UjinTicketResponse
)

data class UjinTicketResponse(
    val id: Long
)
