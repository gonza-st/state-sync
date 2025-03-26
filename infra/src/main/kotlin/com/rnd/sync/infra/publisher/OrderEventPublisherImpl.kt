package com.rnd.sync.infra.publisher

import com.rnd.sync.application.service.order.out.OrderEventPublisher
import com.rnd.sync.common.event.config.DomainEvent
import com.rnd.sync.common.event.delivery.OrderCancelledEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class OrderEventPublisherImpl(
    private val applicationEventPublisher: ApplicationEventPublisher
): OrderEventPublisher {
    override fun publishOrderCancelledEvent(event: DomainEvent<OrderCancelledEvent.Payload>) {
        applicationEventPublisher.publishEvent(event)
    }

    override fun test() {
        TODO("Not yet implemented")
    }
}