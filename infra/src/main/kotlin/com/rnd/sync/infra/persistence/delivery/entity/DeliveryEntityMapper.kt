package com.rnd.sync.infra.persistence.delivery.entity

import com.rnd.sync.application.domain.delivery.Delivery
import com.rnd.sync.application.domain.delivery.DeliveryComposite
import com.rnd.sync.application.domain.deliveryplan.DeliveryPlan.DeliveryPlanId
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
            deliveryPlanId = delivery.deliveryPlanId.id
        )
    }

    fun fromDeliveryCompositeToNewDeliveryEntity(deliveryComposite: DeliveryComposite): DeliveryEntity {
        return DeliveryEntity(
            orderId = deliveryComposite.orderId.id,
            orderNumber = deliveryComposite.orderNumber,
            destination = deliveryComposite.destination,
            driverName = deliveryComposite.driverName,
            deliveryOrder = deliveryComposite.deliveryOrder,
            status = deliveryComposite.status.name(),
            deliveryPlanId = deliveryComposite.deliveryPlan.id.id
        )
    }

    fun fromDeliveryEntityToDeliveryDomain(deliveryEntity: DeliveryEntity): Delivery {
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

        delivery.mapDeliveryPlanId(DeliveryPlanId(deliveryEntity.deliveryPlanId))
        return delivery
    }

    fun fromDeliveryEntityToDeliveryComposite(deliveryEntity: DeliveryEntity): DeliveryComposite {
        val deliveryId = deliveryEntity.id ?: throw IllegalArgumentException("Delivery with id ${deliveryEntity.id} not found")

        val deliveryComposite = DeliveryComposite.create(
            id = deliveryId,
            orderId = deliveryEntity.orderId,
            orderNumber = deliveryEntity.orderNumber,
            destination = deliveryEntity.destination,
            driverName = deliveryEntity.driverName,
            deliveryOrder = deliveryEntity.deliveryOrder,
            status = deliveryEntity.status,
        )

        return deliveryComposite
    }
}