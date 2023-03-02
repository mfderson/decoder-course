package com.ead.course.services.impl

import com.ead.course.dtos.NotificationCommandDto
import com.ead.course.models.CourseModel
import com.ead.course.models.UserModel
import com.ead.course.publishers.NotificationCommandPublisher
import com.ead.course.repositories.CourseRepository
import com.ead.course.repositories.LessonRepository
import com.ead.course.repositories.ModuleRepository
import com.ead.course.services.CourseService
import org.apache.logging.log4j.LogManager
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.lang.Exception
import java.util.*
import javax.transaction.Transactional

@Service
class CourseServiceImpl(
    val courseRepository: CourseRepository,
    val moduleRepository: ModuleRepository,
    val lessonRepository: LessonRepository,
    val notificationCommandPublisher: NotificationCommandPublisher
): CourseService {

    companion object {
        val LOGGER = LogManager.getLogger()
    }

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

        courseRepository.deleteCourseUserByCourse(course.id)

        courseRepository.delete(course)
    }

    override fun save(course: CourseModel) = courseRepository.save(course)

    override fun findById(id: UUID) = courseRepository.findById(id)

    override fun findAll(spec: Specification<CourseModel>?, pageable: Pageable) =
        courseRepository.findAll(spec, pageable)

    override fun existsByCourseAndUser(courseId: UUID, userId: UUID) =
        courseRepository.existsByCourseAndUser(courseId, userId)


    @Transactional
    override fun saveSubscriptionUserInCourse(courseId: UUID, userId: UUID) {
        courseRepository.saveSubscriptionUserInCourse(courseId, userId)
    }

    @Transactional
    override  fun saveSubscriptionUserInCourseAndSendNotification(course: CourseModel, user: UserModel) {
        courseRepository.saveSubscriptionUserInCourse(course.id, user.id)
        try {
            val notificationCommandDto = NotificationCommandDto(
                "Welcome to course ${course.name}",
                "${user.fullName} successfully subscription!",
                user.id
            )
            notificationCommandPublisher.publishNotificatonCommand(notificationCommandDto)
        } catch (e: Exception) {
            LOGGER.warn("Error sending notification subscription command to user: ${user.id}")
        }
    }
}