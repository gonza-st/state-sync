package com.rnd.sync.infra.persistence.delivery

import com.rnd.sync.infra.persistence.delivery.jpa.DeliveryJpaRepository
import org.springframework.stereotype.Repository

@Repository
class DeliveryQuery(
    private val deliveryJpaRepository: DeliveryJpaRepository,
) {
}