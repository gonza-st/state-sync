package com.rnd.sync.application.service.deliveryplan.out

import com.rnd.sync.application.utils.event.DeliveryCancelEvent

interface DeliveryPlanEventPublisher {
    fun deliveryCancelled(event: DeliveryCancelEvent)
}