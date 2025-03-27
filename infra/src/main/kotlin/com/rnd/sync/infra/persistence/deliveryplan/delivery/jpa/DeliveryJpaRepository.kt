package com.rnd.sync.infra.persistence.deliveryplan.delivery.jpa

import com.rnd.sync.infra.persistence.deliveryplan.delivery.entity.DeliveryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DeliveryJpaRepository: JpaRepository<DeliveryEntity, Long> {
    fun findAllByDeliveryPlanId(deliveryPlanId: Long): List<DeliveryEntity>

    fun findByOrderId(orderId: Long): DeliveryEntity?
}