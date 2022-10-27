package com.ead.course.repositories

import com.ead.course.models.ModuleModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface ModuleRepository: JpaRepository<ModuleModel, UUID>, JpaSpecificationExecutor<ModuleModel> {

    @Query(value = "select * from modules where course_id = :courseId", nativeQuery = true)
    fun findAllIntoCourse(@Param("courseId") courseId: UUID): List<ModuleModel>

    @Query(value = "select * from modules where id = :moduleId and course_id = :courseId", nativeQuery = true)
    fun findModuleIntoCourse(
        @Param("moduleId") moduleId: UUID,
        @Param("courseId") courseId: UUID
    ): Optional<ModuleModel>
}