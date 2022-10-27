package com.ead.course.dtos

import javax.validation.constraints.NotBlank

data class LessonDto (
    @field:NotBlank
    val title: String,

    val description: String,

    @field:NotBlank
    val videoUrl: String
)