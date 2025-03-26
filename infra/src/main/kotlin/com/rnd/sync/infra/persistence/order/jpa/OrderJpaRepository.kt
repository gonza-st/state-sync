package com.rnd.sync.infra.persistence.order.jpa

import com.rnd.sync.infra.persistence.order.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OrderJpaRepository: JpaRepository<OrderEntity, Long> {
}