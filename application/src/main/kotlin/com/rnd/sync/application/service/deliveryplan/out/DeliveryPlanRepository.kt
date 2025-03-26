package com.rnd.sync.application.service.deliveryplan.out

import com.rnd.sync.application.domain.delivery.Delivery.DeliveryId
import com.rnd.sync.application.domain.deliveryplan.DeliveryPlan
import com.rnd.sync.application.domain.deliveryplan.DeliveryPlan.DeliveryPlanId

interface DeliveryPlanRepository {
    fun get(deliveryPlanId: DeliveryPlanId): DeliveryPlan

    fun getByDeliveryId(deliveryId: DeliveryId): DeliveryPlan
}