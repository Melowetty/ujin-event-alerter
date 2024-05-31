package ru.melowetty.ujineventalerter.service.impl

import org.springframework.boot.web.client.RestTemplateBuilder
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
import ru.melowetty.ujineventalerter.service.impl.response.MacroscropEventsResponse
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.URL
import java.net.URLConnection
import java.time.Duration
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


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

    override fun getImageFromCamera(id: String): String {
        val url = "${BASE_URL}/site"
        val urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
            .queryParam("login", MACROSCROP_LOGIN)
            .queryParam("password", MACROSCROP_PASSWORD)
            .queryParam("channelid", id)
            .queryParam("resolutionx", 1024)
            .queryParam("resolutiony", 768)
            .encode()
            .toUriString()
        return getByteArrayFromImageURL(urlTemplate)
            ?: throw RuntimeException("Произошла ошибка во время получения изображения с камеры")
    }

    override fun getEvents(eventId: String): MacroscropEventsResponse {
        val url = "${BASE_URL}/event"

        val restTemplate = RestTemplateBuilder()
            .setConnectTimeout(Duration.ofSeconds(5))
            .setReadTimeout(Duration.ofSeconds(10))
            .build()

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.accept = listOf(MediaType.APPLICATION_JSON)

        val urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
            .queryParam("login", MACROSCROP_LOGIN)
            .queryParam("password", MACROSCROP_PASSWORD)
            .queryParam("responsetype", "json")
            .queryParam("filter", eventId)
            .encode()
            .toUriString()

        val entity = HttpEntity(null, headers)

        val response = restTemplate.exchange(
            urlTemplate,
            HttpMethod.GET,
            entity,
            Array<MacroscropEventsResponse>::class.java
        ).body!!

        return response[0]
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun getByteArrayFromImageURL(url: String): String? {
        try {
            val imageUrl: URL = URL(url)
            val ucon: URLConnection = imageUrl.openConnection()
            val `is`: InputStream = ucon.getInputStream()
            val baos: ByteArrayOutputStream = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var read = 0
            while ((`is`.read(buffer, 0, buffer.size).also { read = it }) != -1) {
                baos.write(buffer, 0, read)
            }
            baos.flush()
            return "data:image/jpeg;base64," + Base64.encode(baos.toByteArray())
        } catch (_: Exception) {

        }
        return null
    }

}