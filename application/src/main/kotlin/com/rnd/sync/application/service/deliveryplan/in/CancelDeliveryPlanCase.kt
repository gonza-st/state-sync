package com.rnd.sync.application.service.deliveryplan.`in`

import com.rnd.sync.application.domain.deliveryplan.DeliveryPlan

interface CancelDeliveryPlanCase {
    fun cancelPlan(deliveryPlanId: Long): DeliveryPlan
}