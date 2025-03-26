package com.rnd.sync.infra.publisher

import com.rnd.sync.application.service.deliveryplan.out.DeliveryPlanEventPublisher
import com.rnd.sync.application.utils.event.DeliveryCancelEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class DeliveryPlanEventPublisherImpl(
    private val applicationEventPublisher: ApplicationEventPublisher
) : DeliveryPlanEventPublisher {

    override fun deliveryCancelled(event: DeliveryCancelEvent) {
        applicationEventPublisher.publishEvent(event)
    }
}