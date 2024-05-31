package ru.melowetty.ujineventalerter.service.impl.response

data class UjinTopicGetResponse(
    val error: Int,
    val data: UjinTopicData
)

data class UjinTopicData(
    val ticket: UjinTicketDataResponse
)

data class UjinTicketDataResponse(
    val status: String
)
