package com.rnd.sync.application.service.order.out

import com.rnd.sync.application.domain.order.Order
import com.rnd.sync.application.domain.order.Order.OrderId

interface OrderRepository {
    fun save(order: Order): Order

    fun update(order: Order): Order

    fun get(id: OrderId): Order

    fun getAll(ids: List<OrderId>): List<Order>
}