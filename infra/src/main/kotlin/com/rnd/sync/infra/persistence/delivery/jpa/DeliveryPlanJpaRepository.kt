package com.rnd.sync.infra.persistence.delivery.jpa

import com.rnd.sync.infra.persistence.delivery.entity.DeliveryPlanEntity
import org.springframework.data.jpa.repository.JpaRepository


interface DeliveryPlanJpaRepository : JpaRepository<DeliveryPlanEntity, Long> {
}