package com.ead.course.publishers

import com.ead.course.dtos.NotificationCommandDto
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class NotificationCommandPublisher(
    val rabbitTemplate: RabbitTemplate
) {
    @Value("\${ead.broker.exchange.notificationCommandExchange}")
    lateinit var notificationCommandExchange: String

    @Value("\${ead.broker.key.notificationCommandKey}")
    lateinit var notificationCommandKey: String

    fun publishNotificatonCommand(notificationCommandDto: NotificationCommandDto) {
        rabbitTemplate.convertAndSend(notificationCommandExchange, notificationCommandKey, notificationCommandDto)
    }
}