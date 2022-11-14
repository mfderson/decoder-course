package com.ead.course

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class CourseApplication

fun main(args: Array<String>) {
    runApplication<CourseApplication>(*args)
}
