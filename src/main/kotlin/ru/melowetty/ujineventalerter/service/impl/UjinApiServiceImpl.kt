package ru.melowetty.ujineventalerter.service.impl

import org.springframework.core.env.Environment
import org.springframework.core.env.get
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.melowetty.ujineventalerter.model.EventType
import ru.melowetty.ujineventalerter.model.UjinBuilding
import ru.melowetty.ujineventalerter.service.UjinApiService
import ru.melowetty.ujineventalerter.service.impl.response.UjinAuthResponse
import ru.melowetty.ujineventalerter.service.impl.response.UjinBuildingsResponse
import ru.melowetty.ujineventalerter.service.impl.response.UjinTopicCreateResponse
import ru.melowetty.ujineventalerter.service.impl.response.UjinTopicGetResponse

@Service
class UjinApiServiceImpl(
    private val env: Environment
): UjinApiService {
    private val UJIN_LOGIN = env["app.ujin.login"] ?: ""
    private val UJIN_PASSWORD = env["app.ujin.password"] ?: ""
    private val BASE_UJIN_URL = "https://api-uae-test.ujin.tech/api"
    private var token = auth()
    private val FINISH_STATES = hashSetOf(
        "Completed",
        "Rejected",
        "Returned",
        "Cancelled",
        "Close",
    ).map { it.lowercase() }

    override fun getBuildings(): List<UjinBuilding> {
        val url = "${BASE_UJIN_URL}/v1/buildings/get-list-crm/?token=$token"

        val restTemplate = RestTemplate()

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.accept = listOf(MediaType.APPLICATION_JSON)

        val entity = HttpEntity(headers, headers)

        val response = restTemplate.exchange(url, HttpMethod.GET, entity, UjinBuildingsResponse::class.java).body!!

        if(response.error == 1) {
            token = auth()
            return getBuildings()
        }

        return response.data.buildings.map {
            UjinBuilding(
                id = it.id,
                floor = it.building.floor,
                name = it.building.title,
                location = it.building.address.fullAddress,
            )
        }
    }

    override fun createIncident(buildingId: Long, eventType: EventType, description: String): Long {
        val restTemplate = RestTemplate()

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.accept = listOf(MediaType.APPLICATION_JSON)

        val url = "$BASE_UJIN_URL/v1/tck/bms/tickets/create/"

        val requestBody = mapOf(
            "title" to eventType.title,
            "description" to description,
            "priority" to "high",
            "class" to "inspection",
            "status" to "new",
            "initiator.id" to 1,
            "types" to listOf<Void>(),
            "assignees" to listOf<Void>(),
            "contracting_companies" to listOf<Void>(),
            "objects" to mapOf(
                "type" to "building",
                "id" to buildingId,
            ),
            "planned_start_at" to null,
            "planned_end_at" to null,
            "hide_planned_at_from_resident" to null,
            "extra" to null,

        )

        val entity = HttpEntity(requestBody, headers)

        val response = restTemplate.exchange(url, HttpMethod.POST, entity, UjinTopicCreateResponse::class.java).body!!
        if(response.error == 1) {
            token = auth()
            return createIncident(buildingId, eventType, description)
        }
        return response.data.ticket.id
    }

    override fun getIncidentClosed(topicId: Long): Boolean {
        val restTemplate = RestTemplate()

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.accept = listOf(MediaType.APPLICATION_JSON)

        val url = "$BASE_UJIN_URL/v1/tck/bms/tickets/item/"

        val requestBody = mapOf(
            "ticket" to mapOf(
                "id" to topicId,
            )
        )

        val entity = HttpEntity(requestBody, headers)

        val response = restTemplate.exchange(url, HttpMethod.POST, entity, UjinTopicGetResponse::class.java).body!!
        if(response.error == 1) {
            token = auth()
            return getIncidentClosed(topicId)
        }

        return response.data.ticket.status.lowercase() in FINISH_STATES
    }

    private fun auth(): String {
        val restTemplate = RestTemplate()

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.accept = listOf(MediaType.APPLICATION_JSON)

        val url = "$BASE_UJIN_URL/v1/auth/crm/authenticate"
        val requestBody = mapOf(
            "login" to UJIN_LOGIN,
            "password" to UJIN_PASSWORD
        )

        val entity = HttpEntity(requestBody, headers)

        val response = restTemplate.exchange(url, HttpMethod.POST, entity, UjinAuthResponse::class.java).body!!
        return response.data.user.token
    }

}