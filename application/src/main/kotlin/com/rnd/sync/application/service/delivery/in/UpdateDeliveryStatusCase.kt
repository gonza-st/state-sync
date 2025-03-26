package com.rnd.sync.application.service.delivery.`in`

import com.rnd.sync.application.domain.delivery.Delivery

interface UpdateDeliveryStatusCase {
    fun updateState(request: DeliveryStateUpdateRequest): Delivery

    data class DeliveryStateUpdateRequest(
        val deliveryId: Long,
        val status: String
    ) {}
}