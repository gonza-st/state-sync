package com.rnd.sync.application.service.deliveryplan.out

import com.rnd.sync.application.domain.deliveryplan.DeliveryPlan

interface DeliveryPlanCommandRepository {
    fun save(deliveryPlan: DeliveryPlan): DeliveryPlan

    fun update(deliveryPlan: DeliveryPlan): DeliveryPlan
}