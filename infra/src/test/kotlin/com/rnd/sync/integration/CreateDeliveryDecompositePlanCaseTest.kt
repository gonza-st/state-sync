package com.rnd.sync.integration

import com.rnd.sync.application.domain.order.Order
import com.rnd.sync.application.service.deliveryplan.`in`.CreateDeliveryPlanCase
import com.rnd.sync.application.service.deliveryplan.`in`.CreateDeliveryPlanCase.DeliveryPlanRequest
import com.rnd.sync.application.service.order.out.OrderRepository
import com.rnd.sync.infra.web.SyncApplication
import org.junit.jupiter.api.Assertions.assertNotNull
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
class CreateDeliveryDecompositePlanCaseTest {

    @Autowired
    private lateinit var orderRepository: OrderRepository

    @Autowired
    private lateinit var createDeliveryPlanCase: CreateDeliveryPlanCase

    private lateinit var savedOrders: List<Order>

    @Test
    fun contextLoads() {
    }

    @BeforeEach
    fun setup() {
        savedOrders = (1..5).map { saveOrder(it) }
    }

    @Test
    fun `배차 계획을 생성한다`() {
        val rawOrderIds = savedOrders.map { it.id.id }
        val request = DeliveryPlanRequest(
            deliveryDate = LocalDate.of(2025, 1, 1),
            orderNumbers = rawOrderIds,
        )

        val savedDeliveryPlan = createDeliveryPlanCase.create(request)

        assertNotNull(savedDeliveryPlan.id)
        assertEquals(request.deliveryDate, savedDeliveryPlan.workingDate)
        assertEquals(savedDeliveryPlan.deliveries.size, rawOrderIds.size)
    }

    private fun saveOrder(index: Int): Order {
        val order = Order.createNew(
            productName = "마우스",
            receiverName = "위밋_$index",
            receiverContact = "010-1234-5678",
            receiverAddress = "조원로"
        )

        return orderRepository.save(order)
    }
}