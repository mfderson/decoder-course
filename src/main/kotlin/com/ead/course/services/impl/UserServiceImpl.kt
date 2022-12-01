package com.ead.course.services.impl

import com.ead.course.models.UserModel
import com.ead.course.repositories.UserRepository
import com.ead.course.services.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    val userRepository: UserRepository
): UserService {
    override fun findAll(spec: Specification<UserModel>, pageable: Pageable): Page<UserModel> {
        return userRepository.findAll(spec, pageable)
    }

}