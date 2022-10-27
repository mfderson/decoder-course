package com.ead.course.services

import com.ead.course.models.CourseModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import java.util.*

interface CourseService {

    fun delete(course: CourseModel)
    fun save(course: CourseModel): CourseModel
    fun findById(id: UUID): Optional<CourseModel>
    fun findAll(spec: Specification<CourseModel>?, pageable: Pageable): Page<CourseModel>
}