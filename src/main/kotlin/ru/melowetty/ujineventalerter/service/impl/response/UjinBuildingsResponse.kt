package ru.melowetty.ujineventalerter.service.impl.response

data class UjinBuildingsResponse(
    val error: Int,
    val data: UjinBuildsResponse
)

data class UjinBuildsResponse(
    val buildings: List<UjinBuildInfo>
)

data class UjinBuildInfo(
    val id: Long,
    val building: UjinDetailedBuildInfo
)

data class UjinDetailedBuildInfo(
    val title: String,
    val floor: Int,
    val address: UjinAddressInfo,
)

data class UjinAddressInfo(
    val fullAddress: String,
)


