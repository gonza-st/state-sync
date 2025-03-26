package com.rnd.sync.application.service.order.`in`

import com.rnd.sync.application.domain.order.Order

interface CreateOrderCase {
    fun createOrder(request: OrderRequest): Order

    data class OrderRequest(
        val customerName: String,
        val customerPhoneNumber: String,
        val customerAddress: String,
        val orderedItem: String,
    ) {}
}