package com.rnd.sync.application.utils.event

data class DeliveryCancelEvent(
    val deliveryId: Long,
    val orderId: Long,
) {
}