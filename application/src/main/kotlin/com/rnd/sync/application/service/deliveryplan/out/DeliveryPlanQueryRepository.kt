package com.rnd.sync.application.service.deliveryplan.out

import com.rnd.sync.application.domain.deliveryplan.delivery.Delivery.DeliveryId
import com.rnd.sync.application.domain.deliveryplan.deliveryplan.DeliveryPlan
import com.rnd.sync.application.domain.deliveryplan.deliveryplan.DeliveryPlan.DeliveryPlanId
import com.rnd.sync.application.domain.order.Order.OrderId

interface DeliveryPlanQueryRepository {
    fun get(deliveryPlanId: DeliveryPlanId): DeliveryPlan

    fun getByDeliveryId(deliveryId: DeliveryId): DeliveryPlan

    fun getByOrderId(orderId: OrderId): DeliveryPlan
}