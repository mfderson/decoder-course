package com.ead.course.repositories

import com.ead.course.models.CourseModel
import com.ead.course.models.CourseUserModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface CourseUserRepository: JpaRepository<CourseUserModel, UUID> {

    fun existsByCourseAndUserId(courseModel: CourseModel, userId: UUID): Boolean

    @Query(value = "select * from courses_users where course_id = :courseId", nativeQuery = true)
    fun findAllCourseUserIntoCourse(courseId: UUID): List<CourseUserModel>

    fun existsByUserId(userId: UUID): Boolean

    fun deleteAllByUserId(userId: UUID)
}