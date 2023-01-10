package com.ead.course.dtos

import com.ead.course.models.UserModel
import java.util.UUID

data class UserEventDto(
    val userId: UUID,
    val username: String = "",
    val email: String = "",
    val fullName: String = "",
    val userStatus: String = "",
    val userType: String = "",
    val phoneNumber: String = "",
    val cpf: String = "",
    val imageUrl: String = "",
    var actionType: String = ""
)

fun UserEventDto.convertToUserModel() =
    UserModel(
        id = this.userId,
        email = this.email,
        fullName = this.fullName,
        status = this.userStatus,
        type = this.userType,
        cpf = this.cpf,
        imageUrl = this.imageUrl
    )
