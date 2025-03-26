package com.rnd.sync.application.domain.deliveryplan.deliveryplan

import com.rnd.sync.application.domain.deliveryplan.deliveryplan.DeliveryPlan.DeliveryPlanId
import com.rnd.sync.application.domain.deliveryplan.deliveryplan.state.DeliveryPlanState
import java.time.LocalDate

class DeliveryPlanQuery(
    val deliveryPlanId: DeliveryPlanId,
    val workingDate: LocalDate,
    val status: DeliveryPlanState
) {
}