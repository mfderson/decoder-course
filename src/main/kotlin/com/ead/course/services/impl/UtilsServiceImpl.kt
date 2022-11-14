package com.ead.course.services.impl

import com.ead.course.services.UtilsService
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class UtilsServiceImpl: UtilsService {

    override fun createUrlGetAllUsersByCourse(userId: UUID, pageable: Pageable): String {
        return "/users?courseId=$userId" +
                "&page=${pageable.pageNumber}" +
                "&size=${pageable.pageSize}" +
                "&sort=${pageable.sort.toString().replace(": ", ",")}"
    }
}