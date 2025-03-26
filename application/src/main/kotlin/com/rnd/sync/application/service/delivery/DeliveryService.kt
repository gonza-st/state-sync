package com.rnd.sync.application.service.delivery

import com.rnd.sync.application.domain.delivery.Delivery
import com.rnd.sync.application.domain.delivery.Delivery.DeliveryId
import com.rnd.sync.application.service.delivery.`in`.UpdateDeliveryStatusCase
import com.rnd.sync.application.service.delivery.`in`.UpdateDeliveryStatusCase.DeliveryStateUpdateRequest
import com.rnd.sync.application.service.delivery.out.DeliveryRepository
import org.springframework.stereotype.Service

@Service
class DeliveryService(
    private val deliveryRepository: DeliveryRepository,
): UpdateDeliveryStatusCase {
    override fun updateState(request: DeliveryStateUpdateRequest): Delivery {
        val deliveryId = DeliveryId(request.deliveryId)
        val selectedDelivery = deliveryRepository.get(deliveryId)
        TODO("Not yet implemented")
    }
}