package com.rnd.sync.application.service.deliveryplan.out

import com.rnd.sync.application.domain.deliveryplan.DeliveryPlan

interface DeliveryPlanRepository {
    fun save(deliveryPlan: DeliveryPlan): DeliveryPlan
}