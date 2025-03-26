package com.rnd.sync.application.service.order.`in`

import com.rnd.sync.application.domain.order.Order

interface UpdateOrderStatusCase {
    fun updateState(request: StateUpdateRequest): Order

    data class StateUpdateRequest(
        val orderId: Long,
        val status: String
    ) {}
}