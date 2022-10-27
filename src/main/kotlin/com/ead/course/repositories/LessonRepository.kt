package com.ead.course.repositories

import com.ead.course.models.LessonModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface LessonRepository: JpaRepository<LessonModel, UUID>, JpaSpecificationExecutor<LessonModel> {

    @Query(value = "select * from lessons where module_id = :moduleId", nativeQuery = true)
    fun findAllIntoModule(@Param("moduleId") moduleId: UUID): List<LessonModel>

    @Query(value = "select * from lessons where id = :lessonId and module_id = :moduleId", nativeQuery = true)
    fun findLessonIntoModule(moduleId: UUID, lessonId: UUID): Optional<LessonModel>
}