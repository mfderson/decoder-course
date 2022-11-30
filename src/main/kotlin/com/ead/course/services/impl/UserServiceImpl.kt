package com.ead.course.services.impl

import com.ead.course.repositories.UserRepository
import com.ead.course.services.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    val userRepository: UserRepository
): UserService {

}