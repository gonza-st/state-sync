package com.rnd.sync.application.service.deliveryplan.out

import com.rnd.sync.application.domain.deliveryplan.DeliveryPlan
import com.rnd.sync.application.domain.deliveryplan.DeliveryPlan.DeliveryPlanId

interface DeliveryPlanRepository {
    fun save(deliveryPlan: DeliveryPlan): DeliveryPlan

    fun get(deliveryPlanId: DeliveryPlanId): DeliveryPlan
}