package com.rnd.sync.application.service.order

import com.rnd.sync.application.domain.order.Order
import com.rnd.sync.application.domain.order.state.OrderCreatedState
import com.rnd.sync.application.service.order.`in`.CreateOrderCase
import com.rnd.sync.application.service.order.`in`.CreateOrderCase.OrderRequest
import com.rnd.sync.application.service.order.`in`.UpdateOrderStatusCase
import com.rnd.sync.application.service.order.`in`.UpdateOrderStatusCase.StateUpdateRequest
import com.rnd.sync.application.service.order.out.OrderRepository
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val repository: OrderRepository
) : CreateOrderCase, UpdateOrderStatusCase {
    override fun createOrder(request: OrderRequest): Order {
        val order = Order.createNew(
            productName = request.orderedItem,
            receiverName = request.customerName,
            receiverContact = request.customerPhoneNumber,
            receiverAddress = request.customerAddress,
        )

        val savedOrder = repository.save(order)
        return savedOrder
    }

    override fun updateState(request: StateUpdateRequest): Order {
        val foundOrder = repository.get(Order.OrderId(request.orderId))
        changeStatus(status = request.status, order = foundOrder)

        val updatedOrder = repository.update(foundOrder)
        return updatedOrder
    }

    private fun changeStatus(status: String, order: Order) {
        if (status == OrderCreatedState().name()) {
            order.create()
        } else {
            order.cancel()
        }
    }
}

