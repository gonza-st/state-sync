package com.rnd.sync.application.service.deliveryplan

import com.rnd.sync.application.domain.deliveryplan.DeliveryPlan
import com.rnd.sync.application.domain.deliveryplan.DeliveryPlan.DeliveryPlanId
import com.rnd.sync.application.service.delivery.out.DeliveryRepository
import com.rnd.sync.application.service.deliveryplan.`in`.CancelDeliveryPlanCase
import com.rnd.sync.application.service.deliveryplan.out.DeliveryPlanRepository
import org.springframework.stereotype.Service

@Service
class DeliveryPlanCancelService(
    private val deliveryRepository: DeliveryRepository,
    private val deliveryPlanRepository: DeliveryPlanRepository
) : CancelDeliveryPlanCase {
    override fun cancelPlan(deliveryPlanId: Long): DeliveryPlan {
        val deliveryPlan = deliveryPlanRepository.get(DeliveryPlanId(deliveryPlanId))
        deliveryPlan.cancel()

        // TODO("이건 레포지토리로 내려가야할듯")
        deliveryPlanRepository.save(deliveryPlan)
        deliveryPlan.deliveries.map { deliveryRepository.save(it) }

        return deliveryPlan
    }
}