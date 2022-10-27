package com.ead.course.configs

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.time.format.DateTimeFormatter

//@Configuration
// Comentada pq causa um conflito com a classe de specifications
class DatetimeConfig {
    companion object {
        private const val DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        val LOCAL_DATETIME_SERIALIZER = LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT))
    }

    @Bean
    @Primary
    fun objectMapper(): ObjectMapper {
        val timeModule = JavaTimeModule().addSerializer(LOCAL_DATETIME_SERIALIZER)
        return ObjectMapper().registerModule(timeModule)
    }
}