package com.ead.course.dtos

import java.util.*
import javax.validation.constraints.NotNull

data class UserCourseDto(
    val userId: UUID,

    @field:NotNull
    val courseId: UUID
)
