package com.ead.course.repositories

import com.ead.course.models.CourseModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface CourseRepository: JpaRepository<CourseModel, UUID>, JpaSpecificationExecutor<CourseModel> {

    @Query(value = """
        select case when count(cu) > 0 then true else false end 
        from courses_users cu 
        where cu.course_id = :courseId and cu.user_id = :userId
    """, nativeQuery = true)
    fun existsByCourseAndUser(@Param("courseId") courseId: UUID, @Param("userId") userId: UUID): Boolean

    @Modifying
    @Query(value = """
        insert into courses_users (course_id, user_id) values(:courseId, :userId)
    """, nativeQuery = true)
    fun saveSubscriptionUserInCourse(courseId: UUID, userId: UUID)
}