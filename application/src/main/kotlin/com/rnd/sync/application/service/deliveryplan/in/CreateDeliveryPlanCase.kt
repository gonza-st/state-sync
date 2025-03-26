package com.rnd.sync.application.service.deliveryplan.`in`

import com.rnd.sync.application.domain.deliveryplan.deliveryplan.DeliveryPlan
import java.time.LocalDate

interface CreateDeliveryPlanCase {
    fun create(request: DeliveryPlanRequest): DeliveryPlan

    data class DeliveryPlanRequest(
        val deliveryDate: LocalDate,
        val orderNumbers: List<Long>,
    ) {}
}