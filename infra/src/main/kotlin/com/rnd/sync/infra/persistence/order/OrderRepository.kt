package com.rnd.sync.infra.persistence.order

import com.rnd.sync.infra.persistence.order.jpa.OrderJpaRepository
import org.springframework.stereotype.Repository

@Repository
class OrderRepository(
    private val orderJpaRepository: OrderJpaRepository
) {
}