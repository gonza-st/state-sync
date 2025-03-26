package com.rnd.sync.infra.persistence.delivery.entity

import com.rnd.sync.application.domain.delivery.DeliveryDecomposite
import com.rnd.sync.application.domain.delivery.Delivery
import com.rnd.sync.application.domain.deliveryplan.DeliveryPlan.DeliveryPlanId
import org.springframework.stereotype.Component

@Component
class DeliveryEntityMapper {
    fun fromDeliveryDecompositeToNewDeliveryEntity(deliveryDecomposite: DeliveryDecomposite): DeliveryEntity {
        return DeliveryEntity(
            orderId = deliveryDecomposite.orderId.id,
            orderNumber = deliveryDecomposite.orderNumber,
            destination = deliveryDecomposite.destination,
            driverName = deliveryDecomposite.driverName,
            deliveryOrder = deliveryDecomposite.deliveryOrder,
            status = deliveryDecomposite.status.name(),
            deliveryPlanId = deliveryDecomposite.deliveryPlanId.id
        )
    }

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

    fun fromDeliveryEntityToDeliveryDecomposite(deliveryEntity: DeliveryEntity): DeliveryDecomposite {
        val deliveryId = deliveryEntity.id ?: throw IllegalArgumentException("Delivery with id ${deliveryEntity.id} not found")

        val deliveryDecomposite = DeliveryDecomposite.create(
            id = deliveryId,
            orderId = deliveryEntity.orderId,
            orderNumber = deliveryEntity.orderNumber,
            destination = deliveryEntity.destination,
            driverName = deliveryEntity.driverName,
            deliveryOrder = deliveryEntity.deliveryOrder,
            status = deliveryEntity.status,
        )

        deliveryDecomposite.mapDeliveryPlanId(DeliveryPlanId(deliveryEntity.deliveryPlanId))
        return deliveryDecomposite
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