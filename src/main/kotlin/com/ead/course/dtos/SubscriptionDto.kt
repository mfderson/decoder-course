package com.ead.course.dtos

import java.util.*
import javax.validation.constraints.NotNull

data class SubscriptionDto (
    @field:NotNull
    val userId: UUID)