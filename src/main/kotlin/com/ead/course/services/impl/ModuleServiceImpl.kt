package com.ead.course.services.impl

import com.ead.course.models.ModuleModel
import com.ead.course.repositories.LessonRepository
import com.ead.course.repositories.ModuleRepository
import com.ead.course.services.ModuleService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class ModuleServiceImpl(
    val moduleRepository: ModuleRepository,
    val lessonRepository: LessonRepository
): ModuleService {
    override fun save(module: ModuleModel): ModuleModel {
        return moduleRepository.save(module)
    }

    @Transactional
    override fun delete(module: ModuleModel) {
        val lessons = lessonRepository.findAllIntoModule(module.id)
        if (lessons.isNotEmpty()) {
            lessonRepository.deleteAll(lessons)
        }
        moduleRepository.delete(module)
    }

    override fun findModuleIntoCourse(moduleId: UUID, courseId: UUID): Optional<ModuleModel> {
        return moduleRepository.findModuleIntoCourse(moduleId, courseId)
    }

    override fun findAllByCourse(courseId: UUID): List<ModuleModel> {
        return moduleRepository.findAllIntoCourse(courseId)
    }

    override fun findById(moduleId: UUID): Optional<ModuleModel> {
        return moduleRepository.findById(moduleId)
    }

    override fun findAllByCourse(spec: Specification<ModuleModel>, pageable: Pageable): Page<ModuleModel> {
        return moduleRepository.findAll(spec, pageable)
    }
}