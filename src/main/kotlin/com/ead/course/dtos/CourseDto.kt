package com.ead.course.dtos

import com.ead.course.enums.CourseLevel
import com.ead.course.enums.CourseStatus
import java.util.UUID
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CourseDto(
    @field:NotBlank
    val name: String,

    @field:NotBlank
    val description: String,

    val imageUrl: String,

    @field:NotNull
    val status: CourseStatus,

    @field:NotNull
    val userInstructor: UUID,

    @field:NotNull
    val level: CourseLevel
)