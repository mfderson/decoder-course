package com.ead.course.controllers

import com.ead.course.dtos.ModuleDto
import com.ead.course.models.ModuleModel
import com.ead.course.services.CourseService
import com.ead.course.services.ModuleService
import com.ead.course.specifications.SpecificationTemplate
import org.modelmapper.ModelMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.validation.Valid

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
class ModuleController(
    val moduleService: ModuleService,
    val courseService: CourseService
) {

    companion object {
        val mapper = ModelMapper()
    }

    @PostMapping("/courses/{courseId}/modules")
    fun saveModule(
        @PathVariable courseId: UUID,
        @RequestBody @Valid moduleDto: ModuleDto
    ): ResponseEntity<*> {
        val course = courseService.findById(courseId)
        if (course.isPresent.not()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found")
        }
        val module = mapper.map(moduleDto, ModuleModel::class.java)
        module.creationDate = LocalDateTime.now(ZoneId.of("UTC"))
        module.course = course.get()
        return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.save(module))
    }

    @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
    fun deleteModule(
        @PathVariable courseId: UUID,
        @PathVariable moduleId: UUID
    ): ResponseEntity<*> {
        val module = moduleService.findModuleIntoCourse(moduleId, courseId)
        if (module.isPresent) {
            moduleService.delete(module.get())
            return ResponseEntity.status(HttpStatus.OK).body("Module deleted successfully")
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course")
    }

    @PutMapping("/courses/{courseId}/modules/{moduleId}")
    fun updateModule(
        @PathVariable courseId: UUID,
        @PathVariable moduleId: UUID,
        @RequestBody @Valid moduleDto: ModuleDto
    ): ResponseEntity<*> {
        val module = moduleService.findModuleIntoCourse(moduleId, courseId)
        if (module.isPresent) {
            module.get().apply {
                title = moduleDto.title
                description = moduleDto.description
            }
            return ResponseEntity.status(HttpStatus.OK).body(moduleService.save(module.get()))
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course")
    }

    @GetMapping("/courses/{courseId}/modules")
    fun findAll(
        @PathVariable courseId: UUID,
        spec: SpecificationTemplate.ModuleSpec?,
        @PageableDefault(
            page = 0,
            size = 10,
            sort = ["id"],
            direction = Sort.Direction.ASC
        ) pageable: Pageable
    ): ResponseEntity<Page<ModuleModel>> {
        return ResponseEntity.status(HttpStatus.OK).body(moduleService.findAllByCourse(
            SpecificationTemplate.moduleCourseId(courseId).and(spec),
            pageable
        ))
    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    fun getOne(
        @PathVariable courseId: UUID,
        @PathVariable moduleId: UUID
    ): ResponseEntity<*> {
        val module = moduleService.findModuleIntoCourse(moduleId, courseId)
        if (module.isPresent) {
            return ResponseEntity.status(HttpStatus.OK).body(module.get())
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for this course")
    }
}