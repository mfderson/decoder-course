package com.ead.course.services

import com.ead.course.models.UserModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import java.util.UUID

interface UserService {
    fun findAll(spec: Specification<UserModel>, pageable: Pageable): Page<UserModel>
    fun save(userModel: UserModel): UserModel
    fun delete(userModel: UserModel)

    fun findById(userId: UUID): UserModel?
}