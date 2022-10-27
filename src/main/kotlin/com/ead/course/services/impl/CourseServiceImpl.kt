package com.ead.course.services.impl

import com.ead.course.models.CourseModel
import com.ead.course.repositories.CourseRepository
import com.ead.course.repositories.LessonRepository
import com.ead.course.repositories.ModuleRepository
import com.ead.course.services.CourseService
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class CourseServiceImpl(
    val courseRepository: CourseRepository,
    val moduleRepository: ModuleRepository,
    val lessonRepository: LessonRepository
): CourseService {

    @Transactional
    override fun delete(course: CourseModel) {
        val modules = moduleRepository.findAllIntoCourse(course.id)
        modules.forEach { module ->
            val lessons = lessonRepository.findAllIntoModule(module.id)
            if (lessons.isNotEmpty()) {
                lessonRepository.deleteAll(lessons)
            }
        }
        moduleRepository.deleteAll(modules)
        courseRepository.delete(course)
    }

    override fun save(course: CourseModel) = courseRepository.save(course)

    override fun findById(id: UUID) = courseRepository.findById(id)

    override fun findAll(spec: Specification<CourseModel>?, pageable: Pageable) =
        courseRepository.findAll(spec, pageable)
}