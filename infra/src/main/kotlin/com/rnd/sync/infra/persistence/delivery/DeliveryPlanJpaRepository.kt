package com.rnd.sync.infra.persistence.delivery

import org.springframework.data.jpa.repository.JpaRepository


interface DeliveryPlanJpaRepository : JpaRepository<DeliveryPlanEntity, Long> {
}