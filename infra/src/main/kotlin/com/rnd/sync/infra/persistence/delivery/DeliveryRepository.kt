package com.rnd.sync.infra.persistence.delivery

import org.springframework.stereotype.Repository

@Repository
class DeliveryRepository(
    private val deliveryJpaRepository: DeliveryJpaRepository
) {
}