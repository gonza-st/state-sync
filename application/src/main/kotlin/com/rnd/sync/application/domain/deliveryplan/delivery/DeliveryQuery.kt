package com.rnd.sync.application.domain.deliveryplan.delivery

import com.rnd.sync.application.domain.deliveryplan.delivery.Delivery.DeliveryId
import com.rnd.sync.application.domain.deliveryplan.delivery.state.DeliveryState
import com.rnd.sync.application.domain.deliveryplan.deliveryplan.DeliveryPlan.DeliveryPlanId
import com.rnd.sync.application.domain.order.Order.OrderId

data class DeliveryQuery(
    val deliveryId: DeliveryId,
    val orderId: OrderId,
    val orderNumber: String,
    val destination: String,
    val driverName: String,
    val deliveryOrder: Int,
    val status: DeliveryState,
    val deliveryPlanId: DeliveryPlanId
) {
}