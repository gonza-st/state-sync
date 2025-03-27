package com.rnd.sync.application.service.order.`in`

interface CancelOrderCase {
    fun cancelOrder(request: CancelOrderRequest)

    data class CancelOrderRequest(val orderId: Long)
}