package com.rnd.sync.application.domain.order

import com.rnd.sync.application.domain.order.state.OrderCancelledState
import com.rnd.sync.application.domain.order.state.OrderCreatedState
import com.rnd.sync.application.domain.order.state.OrderState
import java.util.UUID

class Order(
    private val orderId: OrderId? = null,
    val productName: String,
    val orderNumber: String,
    val receiver: Receiver,
    status: OrderState
) {
    val id: OrderId
        get() = orderId ?: throw IllegalStateException("id should not be null")

    var status = status
        private set

    fun create() {
        status = status.create()
    }

    fun cancel() {
        status = status.cancel()
    }

    companion object {
        fun createNew(
            productName: String,
            receiverName: String,
            receiverContact: String,
            receiverAddress: String,
        ): Order {
            val orderNumber = UUID.randomUUID().toString()
            val receiver = Receiver(
                name = receiverName,
                contact = receiverContact,
                address = receiverAddress
            )

            return Order(
                productName = productName,
                orderNumber = orderNumber,
                receiver = receiver,
                status = OrderCreatedState()
            )
        }

        fun create(
            id: Long,
            orderNumber: String,
            productName: String,
            receiverName: String,
            receiverContact: String,
            receiverAddress: String,
            status: String
        ): Order {
            val allStatus = listOf(OrderCreatedState(), OrderCancelledState())
            val selectedStatus = allStatus.find { it.name().equals(status) }
                ?: throw IllegalArgumentException("Order status $status not found")

            val receiver = Receiver(
                name = receiverName,
                contact = receiverContact,
                address = receiverAddress
            )

            return Order(
                orderId = OrderId(id),
                productName = productName,
                orderNumber = orderNumber,
                receiver = receiver,
                status = selectedStatus
            )
        }
    }

    data class OrderId(val id: Long)
}