package com.rnd.sync.application.service.delivery.out

import com.rnd.sync.application.domain.delivery.DeliveryDecomposite
import com.rnd.sync.application.domain.delivery.Delivery.DeliveryId

interface DeliveryRepository {
    fun get(deliveryId: DeliveryId): DeliveryDecomposite
}