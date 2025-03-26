package com.rnd.sync.infra.persistence.deliveryplan.entity

import com.rnd.sync.application.domain.deliveryplan.deliveryplan.DeliveryPlan
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Component

@Component
class DeliveryPlanEntityMapper {
    fun fromDeliveryPlanToNewDeliveryPlanEntity(deliveryPlan: DeliveryPlan): DeliveryPlanEntity {
        return DeliveryPlanEntity(
            workingDate = deliveryPlan.workingDate,
            status = deliveryPlan.status.name(),
        )
    }

    fun fromDeliveryPlanToDeliveryPlanEntity(deliveryPlan: DeliveryPlan): DeliveryPlanEntity {
        return DeliveryPlanEntity(
            id = deliveryPlan.id.id,
            workingDate = deliveryPlan.workingDate,
            status = deliveryPlan.status.name(),
        )
    }

    fun fromDeliveryPlanEntityToDeliveryPlanDomain(deliveryPlanEntity: DeliveryPlanEntity): DeliveryPlan {
        val deliveryPlanId = deliveryPlanEntity.id ?: throw IllegalArgumentException("DeliveryPlan with id $id not found")

        return DeliveryPlan.create(
            id = deliveryPlanId,
            workingDate = deliveryPlanEntity.workingDate,
            status = deliveryPlanEntity.status
        )
    }
}