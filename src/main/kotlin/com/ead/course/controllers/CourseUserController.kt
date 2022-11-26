package com.ead.course.controllers

import com.ead.authuser.enums.UserStatus
import com.ead.course.clients.AuthuserClient
import com.ead.course.dtos.SubscriptionDto
import com.ead.course.dtos.UserDto
import com.ead.course.services.CourseService
import com.ead.course.services.CourseUserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpStatusCodeException
import java.util.*
import javax.validation.Valid

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
class CourseUserController(
    val authuserClient: AuthuserClient,
    val courseService: CourseService,
    val courseUserService: CourseUserService
) {

    @GetMapping("/courses/{courseId}/users")
    fun getAllUsersByCourse(
        @PageableDefault(page = 0, size = 10, sort = ["id"], direction = Sort.Direction.ASC) pageable: Pageable,
        @PathVariable(required = true) courseId: UUID
    ): ResponseEntity<*> {
        val course = courseService.findById(courseId)
        if (!course.isPresent) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found")
        }
        return ResponseEntity.status(HttpStatus.OK).body(authuserClient.getAllUsersByCourse(courseId, pageable))
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

        if (courseUserService.existsByCourseAndUserId(course.get(), subscriptionDto.userId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Subscription already exists!")
        }

        try {
            val userResponse = authuserClient.getUserById(subscriptionDto.userId)
            userResponse.body?.status?.let {
                if (it == UserStatus.BLOCKED)
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("User is blocked")
            }

        } catch (e: HttpStatusCodeException) {
            if (e.statusCode.equals(HttpStatus.NOT_FOUND))
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found")
        }

        val savedCourseUser = courseUserService.saveAndSendSubscriptionInCourse(course.get().convertToCourseUserModel(subscriptionDto.userId))

        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourseUser)
    }

    @DeleteMapping("/courses/users/{userId}")
    fun deleteCourseUserByUser(@PathVariable userId: UUID): ResponseEntity<*> {
        if (courseUserService.existsByUserId(userId).not()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CourseUser not found")
        }
        courseUserService.deleteCourseUserByUser(userId)
        return ResponseEntity.status(HttpStatus.OK).body("CourseUser deleted successfully")
    }
}