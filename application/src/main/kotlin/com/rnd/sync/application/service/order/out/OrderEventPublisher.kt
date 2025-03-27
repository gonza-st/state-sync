package com.rnd.sync.application.service.order.out

import com.rnd.sync.common.event.config.DomainEvent
import com.rnd.sync.common.event.delivery.OrderCancelledEvent

interface OrderEventPublisher {
    fun publishOrderCancelledEvent(event: DomainEvent<OrderCancelledEvent.Payload>)
}