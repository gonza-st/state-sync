package com.rnd.sync.application.service.delivery.`in`

import com.rnd.sync.application.domain.delivery.DeliveryComposite

interface UpdateDeliveryStatusCase {
    fun updateState(request: DeliveryStateUpdateRequest): DeliveryComposite

    data class DeliveryStateUpdateRequest(
        val deliveryId: Long,
        val status: String
    ) {}
}