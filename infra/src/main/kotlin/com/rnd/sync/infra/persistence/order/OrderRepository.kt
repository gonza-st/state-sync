package com.rnd.sync.infra.persistence.order

import org.springframework.stereotype.Repository

@Repository
class OrderRepository(
    private val orderJpaRepository: OrderJpaRepository
) {
}