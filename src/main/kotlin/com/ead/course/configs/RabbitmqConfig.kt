package com.ead.course.configs

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitmqConfig(val cachingConnectionFactory: CachingConnectionFactory) {

    @Bean
    fun rabbitTemplate(): RabbitTemplate {
        val template = RabbitTemplate(cachingConnectionFactory)
        template.messageConverter = messageConverter()
        return template
    }

    @Bean
    fun messageConverter(): Jackson2JsonMessageConverter {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.registerKotlinModule()
        return Jackson2JsonMessageConverter(objectMapper)
    }
}