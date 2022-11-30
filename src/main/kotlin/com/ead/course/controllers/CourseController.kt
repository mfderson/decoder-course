package com.ead.course.controllers

import com.ead.course.dtos.CourseDto
import com.ead.course.models.CourseModel
import com.ead.course.services.CourseService
import com.ead.course.specifications.SpecificationTemplate
import com.ead.course.validations.CourseValidator
import org.modelmapper.ModelMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = ["*"], maxAge = 3600)
class CourseController(
    val courseService: CourseService,
    val courseValidator: CourseValidator
) {

    companion object {
        val mapper = ModelMapper()
    }

    @PostMapping
    fun saveCourse(@RequestBody courseDto: CourseDto, errors: Errors): ResponseEntity<*> {
        courseValidator.validate(courseDto, errors)
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.allErrors)
        }
        val course = mapper.map(courseDto, CourseModel::class.java)
        course.creationDate = LocalDateTime.now(ZoneId.of("UTC"))
        course.lastUpdateDate = LocalDateTime.now(ZoneId.of("UTC"))

        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(course))
    }

    @DeleteMapping("/{id}")
    fun deleteCourse(@PathVariable id: UUID): ResponseEntity<*> {
        val course = courseService.findById(id)
        if (course.isPresent) {
            courseService.delete(course.get())
            return ResponseEntity.status(HttpStatus.OK).body("Course deleted successfully")
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found")
    }

    @PutMapping("/{id}")
    fun updateCourse(
        @PathVariable id: UUID,
        @RequestBody @Valid courseDto: CourseDto
    ): ResponseEntity<*> {
        val courseOptional = courseService.findById(id)
        if (courseOptional.isPresent.not()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found")
        }
        val course = courseOptional.get()
        course.updateByDto(courseDto)
        return ResponseEntity.status(HttpStatus.OK).body(courseService.save(course))
    }

    @GetMapping
    fun getAll(
        spec: SpecificationTemplate.CourseSpec?,
        @PageableDefault(
            page = 0,
            size = 10,
            sort = ["id"],
            direction = Sort.Direction.ASC
        ) pageable: Pageable,
        @RequestParam(required = false) userId: UUID?
    ): ResponseEntity<Page<CourseModel>> {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll(spec, pageable))
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: UUID): ResponseEntity<*> {
        val courseOptional = courseService.findById(id)
        if (courseOptional.isPresent) {
            return ResponseEntity.status(HttpStatus.OK).body(courseOptional.get())
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found")
    }
}