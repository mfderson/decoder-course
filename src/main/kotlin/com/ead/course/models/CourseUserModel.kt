package com.ead.course.models

import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "COURSES_USERS")
data class CourseUserModel(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var course: CourseModel = CourseModel(),

    @Column(nullable = false)
    var userId: UUID = UUID.randomUUID()

): Serializable