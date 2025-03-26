package com.rnd.sync.application.service.delivery

import com.rnd.sync.application.domain.delivery.DeliveryComposite
import com.rnd.sync.application.domain.delivery.DeliveryComposite.DeliveryId
import com.rnd.sync.application.service.delivery.`in`.UpdateDeliveryStatusCase
import com.rnd.sync.application.service.delivery.`in`.UpdateDeliveryStatusCase.DeliveryStateUpdateRequest
import com.rnd.sync.application.service.delivery.out.DeliveryRepository
import org.springframework.stereotype.Service

@Service
class DeliveryService(
    private val deliveryRepository: DeliveryRepository,
): UpdateDeliveryStatusCase {
    override fun updateState(request: DeliveryStateUpdateRequest): DeliveryComposite {
        val deliveryId = DeliveryId(request.deliveryId)
        val selectedDelivery = deliveryRepository.get(deliveryId)
        TODO("Not yet implemented")
    }
}