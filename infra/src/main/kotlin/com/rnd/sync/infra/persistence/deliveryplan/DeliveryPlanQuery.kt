package com.rnd.sync.infra.persistence.deliveryplan

import com.rnd.sync.infra.persistence.deliveryplan.jpa.DeliveryPlanJpaRepository
import org.springframework.stereotype.Repository

@Repository
class DeliveryPlanQuery(
    private val deliveryPlanJpaRepository: DeliveryPlanJpaRepository,
) {
}