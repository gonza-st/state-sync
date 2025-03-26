package com.rnd.sync.integration

import com.rnd.sync.application.service.deliveryplan.`in`.CreateDeliveryPlanCase
import com.rnd.sync.application.service.deliveryplan.`in`.CreateDeliveryPlanCase.DeliveryPlanRequest
import com.rnd.sync.infra.web.SyncApplication
import org.junit.jupiter.api.Assertions.assertNotNull
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
class CreateDeliveryPlanCaseTest {

    @Autowired
    private lateinit var createDeliveryPlanCase: CreateDeliveryPlanCase

    @Test
    fun contextLoads() {}

    @Test
    fun `배차 계획을 생성한다`() {
        val request = DeliveryPlanRequest(
            deliveryDate = LocalDate.of(2025, 1, 1),
            orderNumbers = emptyList(),
        )

        val savedDeliveryPlan = createDeliveryPlanCase.create(request)

        assertNotNull(savedDeliveryPlan.id)
        assertEquals(request.deliveryDate, savedDeliveryPlan.workingDate)
    }
}