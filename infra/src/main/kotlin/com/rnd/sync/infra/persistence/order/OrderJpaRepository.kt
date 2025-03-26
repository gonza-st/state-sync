package com.rnd.sync.infra.persistence.order

import org.springframework.data.jpa.repository.JpaRepository

interface OrderJpaRepository: JpaRepository<OrderEntity, Long> {
}