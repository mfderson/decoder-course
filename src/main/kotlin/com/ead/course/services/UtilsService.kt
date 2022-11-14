package com.ead.course.services

import org.springframework.data.domain.Pageable
import java.util.UUID

interface UtilsService {

    fun createUrlGetAllUsersByCourse(userId: UUID, pageable: Pageable): String
}