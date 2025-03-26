package com.rnd.sync.application.service.deliveryplan

import com.rnd.sync.application.domain.delivery.DeliveryComposite
import com.rnd.sync.application.domain.deliveryplan.DeliveryPlan
import com.rnd.sync.application.domain.order.Order
import com.rnd.sync.application.domain.order.Order.OrderId
import com.rnd.sync.application.service.delivery.out.DeliveryRepository
import com.rnd.sync.application.service.deliveryplan.`in`.CreateDeliveryPlanCase
import com.rnd.sync.application.service.deliveryplan.`in`.CreateDeliveryPlanCase.DeliveryPlanRequest
import com.rnd.sync.application.service.deliveryplan.out.DeliveryPlanRepository
import com.rnd.sync.application.service.order.out.OrderRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class DeliveryPlanService(
    private val deliveryRepository: DeliveryRepository,
    private val orderRepository: OrderRepository,
    private val deliveryPlanRepository: DeliveryPlanRepository
) : CreateDeliveryPlanCase {
    override fun create(request: DeliveryPlanRequest): DeliveryPlan {
        val deliveryPlan = createDeliveryPlan(workingDate = request.deliveryDate)
        val orders = getOrders(rawOrderIds = request.orderNumbers)

        createDeliveries(
            orders = orders,
            deliveryPlan = deliveryPlan,
        )

        return deliveryPlan
    }

    private fun createDeliveryPlan(workingDate: LocalDate): DeliveryPlan {
        val deliveryPlan = DeliveryPlan.createNew(
            workingDate = workingDate
        )
        val savedDeliveryPlan = deliveryPlanRepository.save(deliveryPlan)
        return savedDeliveryPlan
    }

    private fun getOrders(rawOrderIds: List<Long>): List<Order> {
        val orderIds = rawOrderIds.map { OrderId(it) }
        val orders = orderRepository.getAll(orderIds)

        return orders
    }

    private fun createDeliveries(orders: List<Order>, deliveryPlan: DeliveryPlan): List<DeliveryComposite> {
        val deliveries = orders
            .mapIndexed { index, order ->
                DeliveryComposite.createNew(
                    orderId = order.id.id,
                    orderNumber = order.orderNumber,
                    destination = order.receiver.address,
                    driverName = "best driver",
                    deliveryOrder = index,
                )
            }.onEach { it.mapDeliveryPlan(deliveryPlan) }

        deliveries.map { deliveryRepository.save(it) }
        return deliveries
    }
}