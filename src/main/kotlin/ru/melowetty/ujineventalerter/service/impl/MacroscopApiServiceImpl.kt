package ru.melowetty.ujineventalerter.service.impl

import org.springframework.core.ParameterizedTypeReference
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import ru.melowetty.ujineventalerter.model.AvailableCamera
import ru.melowetty.ujineventalerter.service.MacroscopApiService
import ru.melowetty.ujineventalerter.service.impl.response.MacroscopCamerasResponse


@Service
class MacroscopApiServiceImpl(
    private val env: Environment,
): MacroscopApiService {
    private val MACROSCROP_LOGIN = env["app.macroscop.login"] ?: ""
    private val MACROSCROP_PASSWORD = env["app.macroscop.password"] ?: ""
    private val BASE_URL = "http://46.146.226.121:8070"
    override fun getAvailableForConnectionCameras(): List<AvailableCamera> {
        val url = "${BASE_URL}/configure/channels"

        val restTemplate = RestTemplate()

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.accept = listOf(MediaType.APPLICATION_JSON)

        val urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
            .queryParam("login", MACROSCROP_LOGIN)
            .queryParam("password", MACROSCROP_PASSWORD)
            .encode()
            .toUriString()

        val entity = HttpEntity(null, headers)

        val response = restTemplate.exchange(
            urlTemplate,
            HttpMethod.GET,
            entity,
            Array<MacroscopCamerasResponse>::class.java
        ).body!!



        return response.map { AvailableCamera(
            id = it.id,
            name = it.name,
        ) }

    }

}