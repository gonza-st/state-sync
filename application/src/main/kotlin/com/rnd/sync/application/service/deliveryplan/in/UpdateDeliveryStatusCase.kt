package com.rnd.sync.application.service.deliveryplan.`in`

interface UpdateDeliveryStatusCase {
    fun updateState(request: DeliveryStateUpdateRequest)

    data class DeliveryStateUpdateRequest(
        val deliveryId: Long,
        val status: String
    ) {}
}