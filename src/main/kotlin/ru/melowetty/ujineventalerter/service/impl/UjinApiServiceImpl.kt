package ru.melowetty.ujineventalerter.service.impl

import org.springframework.core.env.Environment
import org.springframework.core.env.get
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import ru.melowetty.ujineventalerter.model.UjinBuilding
import ru.melowetty.ujineventalerter.service.UjinApiService
import ru.melowetty.ujineventalerter.service.impl.response.UjinAuthResponse
import ru.melowetty.ujineventalerter.service.impl.response.UjinBuildingsResponse
import ru.melowetty.ujineventalerter.service.impl.response.UjinTopicCreateResponse

@Service
class UjinApiServiceImpl(
    private val env: Environment
): UjinApiService {
    private val UJIN_LOGIN = env["app.ujin.login"] ?: ""
    private val UJIN_PASSWORD = env["app.ujin.password"] ?: ""
    private val BASE_UJIN_URL = "https://api-uae-test.ujin.tech/api"
    private var token = auth()

    override fun getBuildings(): List<UjinBuilding> {
        val url = "${BASE_UJIN_URL}/v1/buildings/get-list-crm/?token=$token"

        val restTemplate = RestTemplate()

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.accept = listOf(MediaType.APPLICATION_JSON)

        val entity = HttpEntity(headers, null)

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

    override fun createIncident(title: String, description: String) {
        // todo происходит ошибка при запросе, необходимо протестировать в постмане
        val restTemplate = RestTemplate()

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.accept = listOf(MediaType.APPLICATION_JSON)

        val url = "$BASE_UJIN_URL/v1/tck/bms/tickets/create/"

        val requestBody = mapOf(
            "title" to title,
            "description" to description,
            "priority" to "high",
            "class" to "default (auto)",
            "status" to "new"
        )

        val entity = HttpEntity(requestBody, headers)

        //val response = restTemplate.exchange(url, HttpMethod.POST, entity, UjinTopicCreateResponse::class.java)
//        if(response.body!!.error == 1) {
//            token = auth()
//            createIncident(title, description)
//        }
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