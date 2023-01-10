package com.ead.course.validations

import com.ead.authuser.enums.UserType
import com.ead.course.dtos.CourseDto
import com.ead.course.services.UserService
import org.apache.catalina.User
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator
import java.util.*

@Component
class CourseValidator(
    @Qualifier("defaultValidator") val validator: Validator,
    val userService: UserService
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
        val userModel = userService.findById(userInstructorId)

        userModel ?: errors.rejectValue(
            "userInstructor",
            "userInstructorError",
            "Instructor not found"
        )

        userModel?.takeIf {
            it.type == UserType.STUDENT.toString()
        }?.apply {
            errors.rejectValue(
                "userInstructor",
                "userInstructorError",
                "User must be INSTRUCTOR or ADMIN"
            )
        }
    }
}