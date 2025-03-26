package com.rnd.sync.infra.persistence.order.entity

import com.rnd.sync.application.domain.order.Order
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Component

@Component
class OrderEntityMapper {
    fun fromOrderToNewOrderEntity(order: Order): OrderEntity {
        return OrderEntity(
            productName = order.productName,
            orderNumber = order.orderNumber,
            receiverName = order.receiver.name,
            receiverContact = order.receiver.contact,
            receiverAddress = order.receiver.address,
            status = order.orderStatus.name()
        )
    }

    fun fromOrderToOrderEntity(order: Order): OrderEntity {
        return OrderEntity(
            id = order.id.id,
            productName = order.productName,
            orderNumber = order.orderNumber,
            receiverName = order.receiver.name,
            receiverContact = order.receiver.contact,
            receiverAddress = order.receiver.address,
            status = order.orderStatus.name()
        )
    }

    fun fromOrderEntityToOrderDomain(orderEntity: OrderEntity): Order {
        val orderId = orderEntity.id ?: throw IllegalArgumentException("Order with id $id not found")

        return Order.create(
            id = orderId,
            orderNumber = orderEntity.orderNumber,
            productName = orderEntity.productName,
            receiverName = orderEntity.receiverName,
            receiverContact = orderEntity.receiverContact,
            receiverAddress = orderEntity.receiverAddress,
            status = orderEntity.status
        )
    }
}