package com.ead.course.clients

import com.ead.course.dtos.ResponsePageDto
import com.ead.course.dtos.UserCourseDto
import com.ead.course.dtos.UserDto
import com.ead.course.services.UtilsService
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import java.util.*


@Component
class AuthuserClient(val restTemplate: RestTemplate, val utilsService: UtilsService) {

    @Value("\${ead.api.url.authuser}")
    lateinit var REQUEST_URL_AUTHUSER: String

    companion object {
        val LOGGER = LogManager.getLogger()
    }

    fun getAllUsersByCourse(courseId: UUID, pageable: Pageable): ResponsePageDto<UserDto>? {
        var result: ResponseEntity<ResponsePageDto<UserDto>>? = null
        val url = REQUEST_URL_AUTHUSER + utilsService.createUrlGetAllUsersByCourse(courseId, pageable)

        LOGGER.debug("Request URL: $url")
        LOGGER.info("Request URL: $url")

        try {
            result = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                object : ParameterizedTypeReference<ResponsePageDto<UserDto>>() {}
            )
            val searchResult = result.body?.content ?: mutableListOf()
            LOGGER.debug("Response number of elements: ${searchResult.size}")
        } catch (e: HttpStatusCodeException) {
            LOGGER.error("Error request $courseId/users ", e)
        }
        LOGGER.info("Ending request to $courseId/users")
        return result?.body
    }

    fun getUserById(userId: UUID): ResponseEntity<UserDto> {
        val url = "$REQUEST_URL_AUTHUSER/users/$userId"
        return restTemplate.exchange(url, HttpMethod.GET, null, UserDto::class.java)
    }

    fun postSubscriptionUserInCourse(courseId: UUID, userId: UUID) {
        val url = "$REQUEST_URL_AUTHUSER/users/$userId/courses/subscription"

        val userCourseDto = UserCourseDto(userId, courseId)

        restTemplate.postForObject(url, userCourseDto, String::class.java)
    }

    fun deleteCourseInAuthUser(courseId: UUID) {
        val url = "$REQUEST_URL_AUTHUSER/users/courses/$courseId"

        restTemplate.exchange(url, HttpMethod.DELETE, null, String::class.java)
    }
}