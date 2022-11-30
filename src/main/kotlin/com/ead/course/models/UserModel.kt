package com.ead.course.models

import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "USERS")
data class UserModel(

    @Id
    val id: UUID = UUID.randomUUID()

): Serializable