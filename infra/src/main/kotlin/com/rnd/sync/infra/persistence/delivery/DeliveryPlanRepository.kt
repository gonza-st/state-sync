package com.rnd.sync.infra.persistence.delivery

import com.rnd.sync.infra.persistence.delivery.jpa.DeliveryPlanJpaRepository
import org.springframework.stereotype.Repository

@Repository
class DeliveryPlanRepository(
    private val deliveryPlanJpaRepository: DeliveryPlanJpaRepository
) {
}