package com.ead.course.models

import com.ead.course.dtos.CourseDto
import com.ead.course.enums.CourseLevel
import com.ead.course.enums.CourseStatus
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import java.io.Serializable
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "COURSES")
data class CourseModel(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false, length = 150)
    var name: String = "",

    @Column(nullable = false, length = 250)
    var description: String = "",

    var imageUrl: String = "",

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    var creationDate: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    var lastUpdateDate: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    var status: CourseStatus = CourseStatus.INPROGRESS,

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    var level: CourseLevel = CourseLevel.BEGINNER,

    @Column(nullable = false)
    var userInstructor: UUID = UUID.randomUUID(),

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    val modules: Set<ModuleModel> = setOf(),

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    val coursesUsers: Set<CourseUserModel> = setOf()
): Serializable {

    infix fun updateByDto(courseDto: CourseDto) {
        this.name = courseDto.name
        this.description = courseDto.description
        this.imageUrl = courseDto.imageUrl
        this.status = courseDto.status
        this.level = courseDto.level
        this.lastUpdateDate = LocalDateTime.now(ZoneId.of("UTC"))
    }

    infix fun convertToCourseUserModel(userId: UUID): CourseUserModel {
        return CourseUserModel(course = this, userId = userId)
    }
}