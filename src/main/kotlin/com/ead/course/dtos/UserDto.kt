package com.ead.course.dtos

import com.ead.authuser.enums.UserStatus
import com.ead.authuser.enums.UserType
import java.util.UUID

data class UserDto(
    val userId: UUID = UUID.randomUUID(),
    val username: String = "",
    val email: String = "",
    val fullName: String = "",
    val status: UserStatus = UserStatus.ACTIVE,
    val type: UserType = UserType.STUDENT,
    val phoneNumber: String = "",
    val cpf: String = "",
    val imageUrl: String = ""
)