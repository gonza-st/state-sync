package com.rnd.sync.application.service.deliveryplan

import com.rnd.sync.application.domain.deliveryplan.DeliveryPlan
import com.rnd.sync.application.domain.order.Order.OrderId
import com.rnd.sync.application.service.deliveryplan.`in`.CreateDeliveryPlanCase
import com.rnd.sync.application.service.deliveryplan.`in`.CreateDeliveryPlanCase.DeliveryPlanRequest
import com.rnd.sync.application.service.deliveryplan.out.DeliveryPlanRepository
import com.rnd.sync.application.service.order.out.OrderRepository
import org.springframework.stereotype.Service

@Service
class DeliveryPlanService(
    private val deliveryPlanRepository: DeliveryPlanRepository
): CreateDeliveryPlanCase {
    override fun create(request: DeliveryPlanRequest): DeliveryPlan {
        val deliveryPlan = DeliveryPlan.createNewDeliveryPlan(
            workingDate = request.deliveryDate
        )

        val savedDeliveryPlan = deliveryPlanRepository.save(deliveryPlan)
        return savedDeliveryPlan
    }
}