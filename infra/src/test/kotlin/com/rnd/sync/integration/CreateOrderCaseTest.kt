package com.rnd.sync.integration

import com.rnd.sync.application.service.order.`in`.CreateOrderCase
import com.rnd.sync.application.service.order.`in`.CreateOrderCase.OrderRequest
import com.rnd.sync.infra.web.SyncApplication
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.annotation.DirtiesContext
import kotlin.test.assertEquals

@SpringBootTest(classes = [SyncApplication::class])
@EnableAutoConfiguration
@EntityScan(basePackages = ["com.rnd.sync"])
@EnableJpaRepositories(basePackages = ["com.rnd.sync"])
@ComponentScan(basePackages = ["com.rnd.sync"])
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class CreateOrderCaseTest {

    @Autowired
    private lateinit var createOrderCase: CreateOrderCase

    @Test
    fun contextLoads() {}

    @Test
    fun `주문을 생성한다`() {
        val request = OrderRequest(
            customerName = "홍길동",
            customerPhoneNumber = "010-1234-5678",
            customerAddress = "서울시 강남구 테헤란로 123, 456동 789호",
            orderedItem = "맥북 프로 16인치"
        )

        val order = createOrderCase.createOrder(request)

        assertNotNull(order.id.id)
        assertNotNull(order.orderNumber)
        assertEquals("created", order.status.name())
        assertEquals(request.customerName, order.receiver.name)
        assertEquals(request.orderedItem, order.productName)
    }
}