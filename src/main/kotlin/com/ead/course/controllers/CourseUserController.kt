package com.ead.course.controllers

import com.ead.authuser.enums.UserStatus
import com.ead.course.dtos.SubscriptionDto
import com.ead.course.services.CourseService
import com.ead.course.services.UserService
import com.ead.course.specifications.SpecificationTemplate
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
class CourseUserController(
    val courseService: CourseService,
    val userService: UserService
) {

    @GetMapping("/courses/{courseId}/users")
    fun getAllUsersByCourse(
        spec: SpecificationTemplate.UserSpec?,
        @PageableDefault(page = 0, size = 10, sort = ["id"], direction = Sort.Direction.ASC) pageable: Pageable,
        @PathVariable(required = true) courseId: UUID
    ): ResponseEntity<*> {
        val course = courseService.findById(courseId)
        if (course.isPresent.not()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found")
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll(
            SpecificationTemplate.userCourseId(courseId).and(spec),
            pageable))
    }

    @PostMapping("/courses/{courseId}/users/subscription")
    fun saveSubscriptionUserInCourse(
        @PathVariable(required = true) courseId: UUID,
        @RequestBody @Valid subscriptionDto: SubscriptionDto
    ): ResponseEntity<*> {
        val course = courseService.findById(courseId)
        if (!course.isPresent) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found")
        }

        if (courseService.existsByCourseAndUser(courseId, subscriptionDto.userId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: subscription already exists")
        }

        val user =  userService.findById(subscriptionDto.userId)

        user ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found")

        if (user.status == UserStatus.BLOCKED.toString()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User is blocked")
        }

        courseService.saveSubscriptionUserInCourseAndSendNotification(course.get(), user)

        return ResponseEntity.status(HttpStatus.CREATED).body("Subscription created successfully")
    }
}