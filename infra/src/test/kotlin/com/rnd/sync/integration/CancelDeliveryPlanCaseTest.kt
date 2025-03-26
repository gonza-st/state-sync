package com.rnd.sync.integration

import com.rnd.sync.application.domain.delivery.Delivery
import com.rnd.sync.application.domain.delivery.state.DeliveryCancelledState
import com.rnd.sync.application.domain.deliveryplan.DeliveryPlan
import com.rnd.sync.application.domain.deliveryplan.state.DeliveryPlanCancelledState
import com.rnd.sync.application.domain.order.Order
import com.rnd.sync.application.service.deliveryplan.`in`.CancelDeliveryPlanCase
import com.rnd.sync.application.service.deliveryplan.out.DeliveryPlanRepository
import com.rnd.sync.application.service.order.out.OrderRepository
import com.rnd.sync.infra.web.SyncApplication
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import java.time.LocalDate
import kotlin.test.assertEquals

@SpringBootTest(classes = [SyncApplication::class])
@EnableAutoConfiguration
@EntityScan(basePackages = ["com.rnd.sync"])
@EnableJpaRepositories(basePackages = ["com.rnd.sync"])
@ComponentScan(basePackages = ["com.rnd.sync"])
class CancelDeliveryPlanCaseTest {

    @Autowired
    private lateinit var orderRepository: OrderRepository

    @Autowired
    private lateinit var deliveryPlanRepository: DeliveryPlanRepository

    @Autowired
    private lateinit var cancelDeliveryPlanCase: CancelDeliveryPlanCase

    private lateinit var savedDeliveryPlan: DeliveryPlan

    @BeforeEach
    fun setup() {
        savedDeliveryPlan = createDeliveryPlan()
    }

    @Test
    fun contextLoads() {
    }


    @Test
    fun `배송 계획이 취소 상태가 되어야한다`() {
        val rawDeliveryPlanId = savedDeliveryPlan.id.id
        val cancelledDeliveryPlan = cancelDeliveryPlanCase.cancelPlan(rawDeliveryPlanId)
        assertEquals(DeliveryPlanCancelledState().name(), cancelledDeliveryPlan.status.name())
    }

    @Test
    fun `배송 계획에 포함된 배송들도 모두 취소 상태가 되어야한다`() {
        val rawDeliveryPlanId = savedDeliveryPlan.id.id
        val cancelledDeliveryPlan = cancelDeliveryPlanCase.cancelPlan(rawDeliveryPlanId)

        cancelledDeliveryPlan.deliveries.forEach { it ->
            assertEquals(DeliveryCancelledState().name(), it.status.name())
        }
    }

    private fun createDeliveryPlan(): DeliveryPlan {
        val orders = (1..5).map { createOrder(it) }
        val savedOrders = orders.map { orderRepository.save(it) }

        val deliveryPlan = DeliveryPlan.createNew(LocalDate.of(2025, 1, 1))
        val deliveries = savedOrders.mapIndexed { index, order -> createDelivery(index, order) }
        deliveries.forEach { it.mapDeliveryPlan(deliveryPlan) }

        val savedDeliveryPlan = deliveryPlanRepository.save(deliveryPlan)
        return savedDeliveryPlan
    }

    private fun createOrder(index: Int): Order {
        val order = Order.createNew(
            productName = "마우스",
            receiverName = "위밋_$index",
            receiverContact = "010-1234-5678",
            receiverAddress = "조원로"
        )

        return orderRepository.save(order)
    }

    private fun createDelivery(index: Int, order: Order): Delivery {
        return Delivery.createNew(
            orderId = order.id.id,
            orderNumber = order.orderNumber,
            destination = order.receiver.address,
            driverName = "driver ${index + 1}",
            deliveryOrder = index
        )
    }
}