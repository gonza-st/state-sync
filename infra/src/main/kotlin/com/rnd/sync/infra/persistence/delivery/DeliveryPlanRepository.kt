package com.rnd.sync.infra.persistence.delivery

import org.springframework.stereotype.Repository

@Repository
class DeliveryPlanRepository(
    private val deliveryPlanJpaRepository: DeliveryPlanJpaRepository
) {
}