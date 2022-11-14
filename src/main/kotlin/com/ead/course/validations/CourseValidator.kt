package com.ead.course.validations

import com.ead.authuser.enums.UserType
import com.ead.course.clients.AuthuserClient
import com.ead.course.dtos.CourseDto
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator
import org.springframework.web.client.HttpStatusCodeException
import java.util.*

@Component
class CourseValidator(
    @Qualifier("defaultValidator") val validator: Validator,
    val authuserClient: AuthuserClient
) : Validator {

    companion object {
        val LOGGER = LogManager.getLogger()
    }

    override fun supports(clazz: Class<*>): Boolean {
        TODO("Not yet implemented")
    }

    override fun validate(target: Any, errors: Errors) {
        val courseDto = target as CourseDto
        validator.validate(courseDto, errors)

        if (errors.hasErrors().not()) {
            validateUserInstructor(courseDto.userInstructor, errors)
        }
    }

    private fun validateUserInstructor(userInstructorId: UUID, errors: Errors) {
        try {
            val userInstructorResponse = authuserClient.getUserById(userInstructorId)
            userInstructorResponse.body?.type
                .takeIf { it != UserType.INSTRUCTOR && it != UserType.ADMIN }
                ?.apply {
                    errors.rejectValue(
                        "userInstructor",
                        "userInstructorError",
                        "User must be INSTRUCTOR or ADMIN"
                    )
                }
        } catch (e: HttpStatusCodeException) {
            LOGGER.info("[validateUserInstructor] ERROR: User must be INSTRUCTOR or ADMIN userId: $userInstructorId", e)
            if (e.statusCode == HttpStatus.NOT_FOUND) {
                errors.rejectValue(
                    "userInstructor",
                    "userInstructorError",
                    "Instructor not found"
                )
            }
        }
    }
}