package com.rnd.sync.infra.persistence.order

import com.rnd.sync.application.domain.order.Order
import com.rnd.sync.application.domain.order.Order.OrderId
import com.rnd.sync.application.service.order.out.OrderRepository
import com.rnd.sync.infra.persistence.order.entity.OrderEntityMapper
import com.rnd.sync.infra.persistence.order.jpa.OrderJpaRepository
import org.springframework.stereotype.Repository

@Repository
class OrderRepositoryImpl(
    private val orderEntityMapper: OrderEntityMapper,
    private val orderJpaRepository: OrderJpaRepository
) : OrderRepository {
    override fun save(order: Order): Order {
        val entity = orderEntityMapper.fromOrderToNewOrderEntity(order)
        val savedEntity = orderJpaRepository.save(entity)

        val savedOrder = orderEntityMapper.fromOrderEntityToOrderDomain(savedEntity)
        return savedOrder
    }

    override fun update(order: Order): Order {
        val entity = orderEntityMapper.fromOrderToOrderEntity(order)
        val savedEntity = orderJpaRepository.save(entity)

        val savedOrder = orderEntityMapper.fromOrderEntityToOrderDomain(savedEntity)
        return savedOrder
    }

    override fun get(id: OrderId): Order {
        val foundEntity = orderJpaRepository.findById(id.id)
            .orElseThrow { IllegalStateException("Order with id $id was not found.") }

        val foundOrder = orderEntityMapper.fromOrderEntityToOrderDomain(foundEntity)
        return foundOrder
    }

    override fun getAll(ids: List<OrderId>): List<Order> {
        val allIds = ids.map { it.id }
        val foundEntities = orderJpaRepository.findAllById(allIds)

        val foundOrders = foundEntities.map { orderEntityMapper.fromOrderEntityToOrderDomain(it) }
        return foundOrders
    }
}