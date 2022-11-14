package com.ead.course.services.impl

import com.ead.course.clients.AuthuserClient
import com.ead.course.models.CourseModel
import com.ead.course.models.CourseUserModel
import com.ead.course.repositories.CourseUserRepository
import com.ead.course.services.CourseUserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CourseUserServiceImpl(
    val courseUserRepository: CourseUserRepository,
    val authuserClient: AuthuserClient
): CourseUserService {

    override fun existsByCourseAndUserId(courseModel: CourseModel, userId: UUID): Boolean {
        return courseUserRepository.existsByCourseAndUserId(courseModel, userId)
    }

    override fun save(courseUserModel: CourseUserModel) =
        courseUserRepository.save(courseUserModel)

    @Transactional
    override fun saveAndSendSubscriptionInCourse(courseUserModel: CourseUserModel): CourseUserModel {
        val savedCourseUser = courseUserRepository.save(courseUserModel)

        authuserClient.postSubscriptionUserInCourse(savedCourseUser.course.id, savedCourseUser.userId)

        return savedCourseUser
    }

}