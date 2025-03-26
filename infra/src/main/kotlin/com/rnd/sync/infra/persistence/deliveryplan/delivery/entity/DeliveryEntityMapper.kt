package com.rnd.sync.infra.persistence.deliveryplan.delivery.entity

import com.rnd.sync.application.domain.deliveryplan.delivery.Delivery
import org.springframework.stereotype.Component

@Component
class DeliveryEntityMapper {
    fun fromDeliveryToNewDeliveryEntity(delivery: Delivery, deliveryPlanId: Long): DeliveryEntity {
        return DeliveryEntity(
            orderId = delivery.orderId.id,
            orderNumber = delivery.orderNumber,
            destination = delivery.destination,
            driverName = delivery.driverName,
            deliveryOrder = delivery.deliveryOrder,
            status = delivery.status.name(),
            deliveryPlanId = deliveryPlanId
        )
    }

    fun fromDeliveryToDeliveryEntity(delivery: Delivery, deliveryPlanId: Long): DeliveryEntity {
        return DeliveryEntity(
            id = delivery.id.id,
            orderId = delivery.orderId.id,
            orderNumber = delivery.orderNumber,
            destination = delivery.destination,
            driverName = delivery.driverName,
            deliveryOrder = delivery.deliveryOrder,
            status = delivery.status.name(),
            deliveryPlanId = deliveryPlanId
        )
    }

    fun fromDeliveryEntityToDelivery(deliveryEntity: DeliveryEntity): Delivery {
        val deliveryId = deliveryEntity.id ?: throw IllegalArgumentException("Delivery with id ${deliveryEntity.id} not found")

        val delivery = Delivery.create(
            id = deliveryId,
            orderId = deliveryEntity.orderId,
            orderNumber = deliveryEntity.orderNumber,
            destination = deliveryEntity.destination,
            driverName = deliveryEntity.driverName,
            deliveryOrder = deliveryEntity.deliveryOrder,
            status = deliveryEntity.status,
        )

        return delivery
    }
}