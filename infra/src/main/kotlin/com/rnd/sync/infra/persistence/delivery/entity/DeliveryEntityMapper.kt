package com.rnd.sync.infra.persistence.delivery.entity

import com.rnd.sync.application.domain.delivery.Delivery
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Component

@Component
class DeliveryEntityMapper {
    fun fromDeliveryToNewDeliveryEntity(delivery: Delivery): DeliveryEntity {
        return DeliveryEntity(
            orderId = delivery.orderId.id,
            orderNumber = delivery.orderNumber,
            destination = delivery.destination,
            driverName = delivery.driverName,
            deliveryOrder = delivery.deliveryOrder,
            status = delivery.status.name(),
            deliveryPlanId = delivery.deliveryPlan.id.id
        )
    }

    fun fromDeliveryEntityToDeliveryDomain(deliveryEntity: DeliveryEntity): Delivery {
        val deliveryId = deliveryEntity.id ?: throw IllegalArgumentException("Delivery with id $id not found")

        return Delivery.createDelivery(
            id = deliveryId,
            orderId = deliveryEntity.orderId,
            orderNumber = deliveryEntity.orderNumber,
            destination = deliveryEntity.destination,
            driverName = deliveryEntity.driverName,
            deliveryOrder = deliveryEntity.deliveryOrder,
            status = deliveryEntity.status,
        )
    }
}