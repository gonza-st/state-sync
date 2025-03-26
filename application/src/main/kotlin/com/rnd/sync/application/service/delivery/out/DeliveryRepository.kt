package com.rnd.sync.application.service.delivery.out

import com.rnd.sync.application.domain.delivery.DeliveryDecomposite
import com.rnd.sync.application.domain.delivery.Delivery
import com.rnd.sync.application.domain.delivery.Delivery.DeliveryId

interface DeliveryRepository {
    fun save(delivery: Delivery): DeliveryId

    fun get(deliveryId: DeliveryId): DeliveryDecomposite
}