package com.rnd.sync.application.service.order

import com.rnd.sync.application.domain.order.Order
import com.rnd.sync.application.domain.order.state.OrderCreatedState
import com.rnd.sync.application.service.order.`in`.CancelOrderCase
import com.rnd.sync.application.service.order.`in`.CreateOrderCase
import com.rnd.sync.application.service.order.`in`.CreateOrderCase.OrderRequest
import com.rnd.sync.application.service.order.`in`.UpdateOrderStatusCase
import com.rnd.sync.application.service.order.`in`.UpdateOrderStatusCase.StateUpdateRequest
import com.rnd.sync.application.service.order.out.OrderEventPublisher
import com.rnd.sync.application.service.order.out.OrderRepository
import com.rnd.sync.common.event.delivery.OrderCancelledEvent
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderEventPublisher: OrderEventPublisher,
) : CreateOrderCase, UpdateOrderStatusCase, CancelOrderCase {
    override fun createOrder(request: OrderRequest): Order {
        val order = Order.createNew(
            productName = request.orderedItem,
            receiverName = request.customerName,
            receiverContact = request.customerPhoneNumber,
            receiverAddress = request.customerAddress,
        )

        val savedOrder = orderRepository.save(order)
        return savedOrder
    }

    override fun updateState(request: StateUpdateRequest): Order {
        val foundOrder = orderRepository.get(Order.OrderId(request.orderId))
        changeStatus(status = request.status, order = foundOrder)

        val updatedOrder = orderRepository.update(foundOrder)
        return updatedOrder
    }

    override fun cancelOrder(request: CancelOrderCase.CancelOrderRequest) {
        val orderId = Order.OrderId(request.orderId)
        val foundOrder = orderRepository.get(orderId)
        changeStatus(status = "cancelled", order = foundOrder)

        orderRepository.update(foundOrder)
    }

    private fun changeStatus(status: String, order: Order) {
        if (status == OrderCreatedState().name()) {
            order.create()
        } else {
            order.cancel()
            publishOrderCancelledEvent(orderId = order.id)
        }
    }

    private fun publishOrderCancelledEvent(orderId: Order.OrderId) {
        val payload = OrderCancelledEvent.Payload(
            orderId = orderId.id
        )

        orderEventPublisher.publishOrderCancelledEvent(OrderCancelledEvent(payload))
    }
}

