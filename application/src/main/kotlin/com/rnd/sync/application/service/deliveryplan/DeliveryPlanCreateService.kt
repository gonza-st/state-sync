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
import java.time.LocalDate

@Service
class DeliveryPlanCreateService(
    private val orderRepository: OrderRepository,
    private val deliveryPlanRepository: DeliveryPlanRepository
) : CreateDeliveryPlanCase {

    override fun create(request: DeliveryPlanRequest): DeliveryPlan {
        val orders = getOrders(rawOrderIds = request.orderNumbers)

        val deliveryPlan = generateDeliveryPlan(
            workingDate = request.deliveryDate,
            orders = orders
        )

        val savedDeliveryPlan = deliveryPlanRepository.save(deliveryPlan)
        return savedDeliveryPlan
    }

    private fun generateDeliveryPlan(workingDate: LocalDate, orders: List<Order>): DeliveryPlan {
        val deliveries = orders.mapIndexed { index, order -> generateDelivery(index, order) }
        val deliveryPlan = DeliveryPlan.createNew(workingDate)
        deliveries.forEach { it.mapDeliveryPlan(deliveryPlan) }

        return deliveryPlan
    }

    private fun generateDelivery(index: Int, order: Order): Delivery {
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