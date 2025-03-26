package com.rnd.sync.infra.persistence.deliveryplan.jpa

import com.rnd.sync.infra.persistence.deliveryplan.entity.DeliveryPlanEntity
import org.springframework.data.jpa.repository.JpaRepository


interface DeliveryPlanJpaRepository : JpaRepository<DeliveryPlanEntity, Long> {
}