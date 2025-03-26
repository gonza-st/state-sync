package com.rnd.sync.application.service.delivery.out

import com.rnd.sync.application.domain.delivery.Delivery
import com.rnd.sync.application.domain.delivery.DeliveryComposite
import com.rnd.sync.application.domain.delivery.DeliveryComposite.DeliveryId

interface DeliveryRepository {
    fun save(delivery: Delivery): Delivery

    fun save(deliveryComposite: DeliveryComposite): DeliveryId

    fun get(deliveryId: DeliveryId): Delivery
}