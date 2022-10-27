package com.ead.course.specifications

import com.ead.course.models.CourseModel
import com.ead.course.models.LessonModel
import com.ead.course.models.ModuleModel
import net.kaczmarzyk.spring.data.jpa.domain.Equal
import net.kaczmarzyk.spring.data.jpa.domain.Like
import net.kaczmarzyk.spring.data.jpa.web.annotation.And
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec
import org.springframework.data.jpa.domain.Specification
import java.util.*
import javax.persistence.criteria.Expression
import javax.persistence.criteria.Root

class SpecificationTemplate {

    companion object {
        fun moduleCourseId(courseId: UUID): Specification<ModuleModel> {
            return Specification<ModuleModel> { root, query, criteriaBuilder ->
                query.distinct(true)
                val module: Root<ModuleModel> = root
                val course: Root<CourseModel> = query.from(CourseModel::class.java)
                val coursesModules: Expression<Collection<ModuleModel>> = course.get("modules")
                return@Specification criteriaBuilder.and(
                    criteriaBuilder.equal(course.get<UUID>("id"), courseId),
                    criteriaBuilder.isMember(module, coursesModules)
                )

            }
        }

        fun lessonModuleId(moduleId: UUID): Specification<LessonModel> {
            return Specification<LessonModel> { root, query, criteriaBuilder ->
                query.distinct(true)
                val lesson: Root<LessonModel> = root
                val module: Root<ModuleModel> = query.from(ModuleModel::class.java)
                val moduleLessons: Expression<Collection<LessonModel>> = module.get("lessons")
                return@Specification criteriaBuilder.and(
                    criteriaBuilder.equal(module.get<UUID>("id"), moduleId),
                    criteriaBuilder.isMember(lesson, moduleLessons)
                )

            }
        }
    }

    @And(
        Spec(path = "level", spec = Equal::class),
        Spec(path = "status", spec = Equal::class),
        Spec(path = "name", spec = Like::class)
    )
    interface CourseSpec: Specification<CourseModel> {}


    @Spec(path = "title", spec = Like::class)
    interface ModuleSpec: Specification<ModuleModel> {}

    @Spec(path = "title", spec = Like::class)
    interface LessonSpec: Specification<LessonModel> {}


}