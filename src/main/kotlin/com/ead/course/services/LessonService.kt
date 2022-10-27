package com.ead.course.services

import com.ead.course.models.LessonModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import java.util.*

interface LessonService {
    fun save(lesson: LessonModel): LessonModel
    fun findLessonIntoModule(moduleId: UUID, lessonId: UUID): Optional<LessonModel>
    fun delete(lesson: LessonModel)
    fun findAllByModule(moduleId: UUID): List<LessonModel>
    fun findAllByModule(spec: Specification<LessonModel>, pageable: Pageable): Page<LessonModel>
}