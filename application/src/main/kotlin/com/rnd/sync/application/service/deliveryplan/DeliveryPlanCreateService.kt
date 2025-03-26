package com.rnd.sync.application.service.deliveryplan

import com.rnd.sync.application.domain.delivery.Delivery
import com.rnd.sync.application.domain.deliveryplan.DeliveryPlan
import com.rnd.sync.application.domain.order.Order
import com.rnd.sync.application.domain.order.Order.OrderId
import com.rnd.sync.application.service.deliveryplan.`in`.CreateDeliveryPlanCase
import com.rnd.sync.application.service.deliveryplan.`in`.CreateDeliveryPlanCase.DeliveryPlanRequest
import com.rnd.sync.application.service.deliveryplan.out.DeliveryPlanRepository
import com.rnd.sync.application.service.order.out.OrderRepository
import org.springframework.stereotype.Service

@Service
class DeliveryPlanCreateService(
    private val orderRepository: OrderRepository,
    private val deliveryPlanRepository: DeliveryPlanRepository
) : CreateDeliveryPlanCase {
    override fun create(request: DeliveryPlanRequest): DeliveryPlan {
        val orders = getOrders(rawOrderIds = request.orderNumbers)

        val deliveries = orders.mapIndexed { index, order -> createDelivery(index, order) }
        val deliveryPlan = DeliveryPlan.createNew(workingDate = request.deliveryDate)
        deliveries.forEach { it.mapDeliveryPlan(deliveryPlan) }

        val savedDeliveryPlan = deliveryPlanRepository.save(deliveryPlan)
        return savedDeliveryPlan
    }

    private fun createDelivery(index: Int, order: Order): Delivery {
        return Delivery.createNew(
            orderId = order.id.id,
            orderNumber = order.orderNumber,
            destination = order.receiver.address,
            driverName = "best driver",
            deliveryOrder = index,
        )
    }

    // TODO ("order service로 이동 고려하기")
    private fun getOrders(rawOrderIds: List<Long>): List<Order> {
        val orderIds = rawOrderIds.map { OrderId(it) }
        val orders = orderRepository.getAll(orderIds)

        return orders
    }
}