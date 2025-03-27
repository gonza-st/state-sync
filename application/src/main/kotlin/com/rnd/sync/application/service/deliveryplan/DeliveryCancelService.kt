package com.rnd.sync.application.service.deliveryplan

import com.rnd.sync.application.domain.order.Order.OrderId
import com.rnd.sync.application.service.deliveryplan.`in`.CancelDeliveryCase
import com.rnd.sync.application.service.deliveryplan.`in`.CancelDeliveryCase.CancelDeliveryCaseRequest
import com.rnd.sync.application.service.deliveryplan.out.DeliveryPlanCommandRepository
import com.rnd.sync.application.service.deliveryplan.out.DeliveryPlanQueryRepository
import org.springframework.stereotype.Component

@Component
class DeliveryCancelService(
    private val deliveryPlanQueryRepository: DeliveryPlanQueryRepository,
    private val deliveryPlanCommandRepository: DeliveryPlanCommandRepository
): CancelDeliveryCase {
    override fun cancelDelivery(request: CancelDeliveryCaseRequest) {
        val orderId = OrderId(request.orderId)
        val deliveryPlan =  deliveryPlanQueryRepository.getByOrderId(orderId)
        deliveryPlan.cancelDeliveryByOrderId(orderId.id)

        deliveryPlanCommandRepository.update(deliveryPlan)
    }
}