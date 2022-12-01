package com.ead.course.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonMappingException
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "USERS")
data class UserModel(

    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false, unique = true, length = 50)
    val email: String = "",

    @Column(nullable = false, length = 150)
    val fullName: String = "",

    @Column(nullable = false)
    val userStatus: String = "",

    @Column(nullable = false)
    val userType: String = "",

    @Column(length = 20)
    val cpf: String = "",

    @Column
    val imageUrl: String = "",

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    val courses: Set<CourseModel> = setOf()

): Serializable