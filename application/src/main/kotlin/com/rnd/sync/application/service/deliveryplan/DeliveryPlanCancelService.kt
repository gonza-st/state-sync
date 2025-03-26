package com.rnd.sync.application.service.deliveryplan

import com.rnd.sync.application.domain.deliveryplan.DeliveryPlan
import com.rnd.sync.application.domain.deliveryplan.DeliveryPlan.DeliveryPlanId
import com.rnd.sync.application.service.deliveryplan.`in`.CancelDeliveryPlanCase
import com.rnd.sync.application.service.deliveryplan.out.DeliveryPlanCommandRepository
import com.rnd.sync.application.service.deliveryplan.out.DeliveryPlanQueryRepository
import org.springframework.stereotype.Service

@Service
class DeliveryPlanCancelService(
    private val deliveryPlanQueryRepository: DeliveryPlanQueryRepository,
    private val deliveryPlanCommandRepository: DeliveryPlanCommandRepository
) : CancelDeliveryPlanCase {
    override fun cancelPlan(deliveryPlanId: Long): DeliveryPlan {
        val deliveryPlan = deliveryPlanQueryRepository.get(DeliveryPlanId(deliveryPlanId))
        deliveryPlan.cancel()

        deliveryPlanCommandRepository.update(deliveryPlan)
        return deliveryPlan
    }
}