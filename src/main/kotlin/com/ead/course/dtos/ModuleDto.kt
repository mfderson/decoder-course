package com.ead.course.dtos

import javax.validation.constraints.NotBlank

data class ModuleDto (
    @field:NotBlank
    val title: String,

    @field:NotBlank
    val description: String
)