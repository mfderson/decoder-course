package com.ead.course.services

import com.ead.course.models.ModuleModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import java.util.*

interface ModuleService {

    fun save(module: ModuleModel): ModuleModel
    fun delete(module: ModuleModel)
    fun findModuleIntoCourse(moduleId: UUID, courseId: UUID): Optional<ModuleModel>
    fun findAllByCourse(courseId: UUID): List<ModuleModel>
    fun findById(moduleId: UUID): Optional<ModuleModel>
    fun findAllByCourse(spec: Specification<ModuleModel>, pageable: Pageable): Page<ModuleModel>
}