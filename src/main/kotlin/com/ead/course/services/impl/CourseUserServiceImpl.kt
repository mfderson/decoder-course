package com.ead.course.services.impl

import com.ead.course.repositories.CourseUserRepository
import com.ead.course.services.CourseUserService
import org.springframework.stereotype.Service

@Service
class CourseUserServiceImpl(
    val courseUserRepository: CourseUserRepository
): CourseUserService {
}