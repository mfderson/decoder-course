package com.ead.course.services.impl

import com.ead.course.models.UserModel
import com.ead.course.repositories.CourseRepository
import com.ead.course.repositories.UserRepository
import com.ead.course.services.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class UserServiceImpl(
    val userRepository: UserRepository,
    val courseRepository: CourseRepository
): UserService {
    override fun findAll(spec: Specification<UserModel>, pageable: Pageable): Page<UserModel> {
        return userRepository.findAll(spec, pageable)
    }

    override fun save(userModel: UserModel) =
        userRepository.save(userModel)

    @Transactional
    override fun delete(userModel: UserModel) {
        courseRepository.deleteCourseUserByUser(userModel.id)
        userRepository.deleteById(userModel.id)
    }

    override fun findById(userId: UUID) =
        userRepository.findByIdOrNull(userId)
}