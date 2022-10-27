package com.ead.course.controllers

import com.ead.course.dtos.LessonDto
import com.ead.course.dtos.ModuleDto
import com.ead.course.models.LessonModel
import com.ead.course.models.ModuleModel
import com.ead.course.services.CourseService
import com.ead.course.services.LessonService
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
class LessonController(
    val moduleService: ModuleService,
    val lessonService: LessonService
) {

    companion object {
        val mapper = ModelMapper()
    }

    @PostMapping("/modules/{moduleId}/lessons")
    fun saveLesson(
        @PathVariable moduleId: UUID,
        @RequestBody @Valid lessonDto: LessonDto
    ): ResponseEntity<*> {
        val module = moduleService.findById(moduleId)
        if (module.isPresent) {
            val lesson = mapper.map(lessonDto, LessonModel::class.java)
            lesson.module = module.get()
            return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.save(lesson))
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found")
    }

    @DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
    fun deleteLesson(
        @PathVariable moduleId: UUID,
        @PathVariable lessonId: UUID
    ): ResponseEntity<*> {
        val lesson = lessonService.findLessonIntoModule(moduleId, lessonId)
        if (lesson.isPresent) {
            lessonService.delete(lesson.get())
            return ResponseEntity.status(HttpStatus.OK).body("Lesson deleted successfully")
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module")
    }

    @PutMapping("/modules/{moduleId}/lessons/{lessonId}")
    fun updateLesson(
        @PathVariable moduleId: UUID,
        @PathVariable lessonId: UUID,
        @RequestBody @Valid lessonDto: LessonDto
    ): ResponseEntity<*> {
        val lesson = lessonService.findLessonIntoModule(moduleId, lessonId)
        if (lesson.isPresent) {
            lesson.get().apply {
                title = lessonDto.title
                description = lessonDto.description
            }
            return ResponseEntity.status(HttpStatus.OK).body(lessonService.save(lesson.get()))
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module")
    }

    @GetMapping("/modules/{moduleId}/lessons")
    fun findAll(
        @PathVariable moduleId: UUID,
        spec: SpecificationTemplate.LessonSpec?,
        @PageableDefault(
            page = 0,
            size = 10,
            sort = ["id"],
            direction = Sort.Direction.ASC
        ) pageable: Pageable
    ): ResponseEntity<Page<LessonModel>> {
        return ResponseEntity.status(HttpStatus.OK).body(lessonService.findAllByModule(
            SpecificationTemplate.lessonModuleId(moduleId).and(spec),
            pageable
        ))
    }

    @GetMapping("/modules/{moduleId}/lessons/{lessonId}")
    fun getOne(
        @PathVariable moduleId: UUID,
        @PathVariable lessonId: UUID
    ): ResponseEntity<*> {
        val lesson = lessonService.findLessonIntoModule(moduleId, lessonId)
        if (lesson.isPresent) {
            return ResponseEntity.status(HttpStatus.OK).body(lesson.get())
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for this module")
    }
}