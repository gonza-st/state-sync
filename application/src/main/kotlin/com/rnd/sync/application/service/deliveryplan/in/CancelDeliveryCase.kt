package com.rnd.sync.application.service.deliveryplan.`in`

interface CancelDeliveryCase {
    fun cancelDelivery(request: CancelDeliveryCaseRequest)

    data class CancelDeliveryCaseRequest(val orderId: Long) {}
}