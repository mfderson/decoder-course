package com.ead.course.repositories

import com.ead.course.models.CourseUserModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CourseUserRepository: JpaRepository<CourseUserModel, UUID> {
}