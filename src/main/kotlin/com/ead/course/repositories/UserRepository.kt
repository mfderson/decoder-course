package com.ead.course.repositories

import com.ead.course.models.UserModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.*

interface UserRepository: JpaRepository<UserModel, UUID>, JpaSpecificationExecutor<UserModel> {

}