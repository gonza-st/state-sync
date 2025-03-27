package com.rnd.sync.common.event.delivery

import com.rnd.sync.common.event.config.DomainEvent
import com.rnd.sync.common.event.config.EventType

class OrderCancelledEvent(
    payload: Payload
) : DomainEvent<OrderCancelledEvent.Payload>(EventType.ORDER_CANCELLED, payload) {

    data class Payload(val orderId: Long) {}

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}