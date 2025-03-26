package com.rnd.sync.application.service.delivery.out

import com.rnd.sync.application.domain.delivery.Delivery

interface DeliveryRepository {
    fun save(delivery: Delivery): Delivery
}