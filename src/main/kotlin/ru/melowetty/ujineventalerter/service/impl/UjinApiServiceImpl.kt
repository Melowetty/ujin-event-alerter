package ru.melowetty.ujineventalerter.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
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
    private val env: Environment,
    private val objectMapper: ObjectMapper,
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

    override fun createIncident(buildingIdExternal: Long, eventType: EventType, description: String): Long {
        val restTemplate = RestTemplate()

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.accept = listOf(MediaType.APPLICATION_JSON)

        val url = "$BASE_UJIN_URL/v1/tck/bms/tickets/create?token=$token"

        val requestBody = jsonStringToMapWithJackson("""
            {
              "title": "${eventType.title}",
              "description": "${description}",
              "priority": "high",
              "class": "inspection",
              "status": "new",
              "initiator.id": 1,
              "types": [${if(eventType.getTypes().size > 0) eventType.getTypes().first() else ""}],
              "assignees": [],
              "contracting_companies": [],
              "objects": [
                {
                  "type": "building",
                   "id": ${buildingIdExternal}
                }
              ],
              "planned_start_at": null,
              "planned_end_at": null,
              "hide_planned_at_from_resident": null,
              "extra": null
            }
        """.trimIndent())

        val entity = HttpEntity(requestBody, headers)

        val response = restTemplate.exchange(url, HttpMethod.POST, entity, UjinTopicCreateResponse::class.java).body!!
        if(response.error == 1) {
            token = auth()
            return createIncident(buildingIdExternal, eventType, description)
        }
        return response.data.ticket.id
    }

    override fun getIncidentClosed(topicId: Long): Boolean {
        val restTemplate = RestTemplate()

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.accept = listOf(MediaType.APPLICATION_JSON)

        val url = "$BASE_UJIN_URL/v1/tck/bms/tickets/item?token=$token"
        val requestBody = jsonStringToMapWithJackson("""
            {
              "ticket": {
                "id": ${topicId} 
              }
            }
        """.trimIndent())

        val entity = HttpEntity(requestBody, headers)

        val response = restTemplate.exchange(url, HttpMethod.POST, entity, String::class.java).body!!
//        if(response.error == 1) {
//            token = auth()
//            return getIncidentClosed(topicId)
//        }
//
//        return response.data.ticket.status.lowercase() in FINISH_STATES
        return true
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

    fun jsonStringToMapWithJackson(json: String): Map<String, Any> {
        val objectMapper = jacksonObjectMapper()
        return objectMapper.readValue<Map<String, Any>>(json)
    }
}