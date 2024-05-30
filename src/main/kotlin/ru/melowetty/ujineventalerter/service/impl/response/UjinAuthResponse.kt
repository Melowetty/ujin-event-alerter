package ru.melowetty.ujineventalerter.service.impl.response

data class UjinAuthResponse(
    val error: Int,
    val data: UjinDataResponse
)

data class UjinDataResponse(
    val user: UjinUserResponse
)

data class UjinUserResponse(
    val token: String,
)