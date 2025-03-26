package com.rnd.sync.infra.persistence.delivery.jpa

import com.rnd.sync.infra.persistence.delivery.entity.DeliveryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DeliveryJpaRepository: JpaRepository<DeliveryEntity, Long> {
    fun findAllByDeliveryPlanId(deliveryPlanId: Long): List<DeliveryEntity>
}