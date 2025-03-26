package com.rnd.sync.integration

import com.rnd.sync.application.domain.delivery.Delivery
import com.rnd.sync.application.domain.delivery.Delivery.DeliveryId
import com.rnd.sync.application.domain.deliveryplan.DeliveryPlan
import com.rnd.sync.application.domain.order.Order
import com.rnd.sync.application.service.deliveryplan.`in`.UpdateDeliveryStatusCase
import com.rnd.sync.application.service.deliveryplan.`in`.UpdateDeliveryStatusCase.DeliveryStateUpdateRequest
import com.rnd.sync.application.service.deliveryplan.out.DeliveryPlanCommandRepository
import com.rnd.sync.application.service.deliveryplan.out.DeliveryPlanQueryRepository
import com.rnd.sync.application.service.order.out.OrderRepository
import com.rnd.sync.infra.web.SyncApplication
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.annotation.DirtiesContext
import java.time.LocalDate
import kotlin.test.assertEquals

@SpringBootTest(classes = [SyncApplication::class])
@EnableAutoConfiguration
@EntityScan(basePackages = ["com.rnd.sync"])
@EnableJpaRepositories(basePackages = ["com.rnd.sync"])
@ComponentScan(basePackages = ["com.rnd.sync"])
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class UpdateDeliveryStatusCaseTest {

    @Autowired
    private lateinit var orderRepository: OrderRepository

    @Autowired
    private lateinit var deliveryPlanQueryRepository: DeliveryPlanQueryRepository

    @Autowired
    private lateinit var deliveryPlanCommandRepository: DeliveryPlanCommandRepository

    @Autowired
    private lateinit var updateDeliveryStatusCase: UpdateDeliveryStatusCase

    private lateinit var savedDeliveryPlan: DeliveryPlan

    @BeforeEach
    fun setup() {
        savedDeliveryPlan = createDeliveryPlan()
    }

    @Test
    fun contextLoads() {
    }

    @Test
    fun `배송을 취소할 수 있다`() {
        val rawDeliveryId = 2L
        val request = DeliveryStateUpdateRequest(
            deliveryId = rawDeliveryId,
            status = "cancelled"
        )

        updateDeliveryStatusCase.updateState(request)

        val deliveryId = DeliveryId(rawDeliveryId)
        val foundDeliveryPlan = deliveryPlanQueryRepository.getByDeliveryId(deliveryId)

        val ex = assertThrows<IllegalStateException> {
            foundDeliveryPlan.cancelDelivery(deliveryId)
        }

        assertEquals("이미 취소된 상태입니다", ex.message)
    }

    private fun createDeliveryPlan(): DeliveryPlan {
        val orders = (1..5).map { createOrder(it) }
        val savedOrders = orders.map { orderRepository.save(it) }

        val deliveryPlan = DeliveryPlan.createNew(LocalDate.of(2025, 1, 1))
        val deliveries = savedOrders.mapIndexed { index, order -> createDelivery(index, order) }
        deliveries.forEach { it.mapDeliveryPlan(deliveryPlan) }

        val savedDeliveryPlan = deliveryPlanCommandRepository.save(deliveryPlan)
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