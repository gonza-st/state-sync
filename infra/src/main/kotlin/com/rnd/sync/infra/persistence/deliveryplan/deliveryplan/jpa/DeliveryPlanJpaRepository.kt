package com.rnd.sync.infra.persistence.deliveryplan.deliveryplan.jpa

import com.rnd.sync.infra.persistence.deliveryplan.deliveryplan.entity.DeliveryPlanEntity
import org.springframework.data.jpa.repository.JpaRepository


interface DeliveryPlanJpaRepository : JpaRepository<DeliveryPlanEntity, Long> {
}