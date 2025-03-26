package com.rnd.sync.infra.persistence.deliveryplan

import com.rnd.sync.application.domain.deliveryplan.delivery.Delivery.DeliveryId
import com.rnd.sync.application.domain.deliveryplan.deliveryplan.DeliveryPlan
import com.rnd.sync.application.domain.deliveryplan.deliveryplan.DeliveryPlan.DeliveryPlanId
import com.rnd.sync.application.service.deliveryplan.out.DeliveryPlanQueryRepository
import com.rnd.sync.infra.persistence.delivery.entity.DeliveryEntity
import com.rnd.sync.infra.persistence.delivery.entity.DeliveryEntityMapper
import com.rnd.sync.infra.persistence.delivery.jpa.DeliveryJpaRepository
import com.rnd.sync.infra.persistence.deliveryplan.entity.DeliveryPlanEntity
import com.rnd.sync.infra.persistence.deliveryplan.entity.DeliveryPlanEntityMapper
import com.rnd.sync.infra.persistence.deliveryplan.jpa.DeliveryPlanJpaRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Repository

@Repository
class DeliveryPlanQueryRepositoryImpl(
    private val deliveryEntityMapper: DeliveryEntityMapper,
    private val deliveryJpaRepository: DeliveryJpaRepository,
    private val deliveryPlanEntityMapper: DeliveryPlanEntityMapper,
    private val deliveryPlanJpaRepository: DeliveryPlanJpaRepository,
) : DeliveryPlanQueryRepository {
    override fun get(deliveryPlanId: DeliveryPlanId): DeliveryPlan {
        val deliveryPlanEntity = getDeliveryPlanEntityById(deliveryPlanId)
        val deliveryEntities = getAllDeliveriesByDeliveryId(deliveryPlanId)

        val deliveryPlan = mapToDeliveryPlan(deliveryPlanEntity, deliveryEntities)
        return deliveryPlan
    }

    override fun getByDeliveryId(deliveryId: DeliveryId): DeliveryPlan {
        val deliveryEntity = deliveryJpaRepository.findById(deliveryId.id)
            .orElseThrow { EntityNotFoundException("Delivery with id $deliveryId not found") }

        val deliveryPlanId = DeliveryPlanId(deliveryEntity.deliveryPlanId)
        val deliveryPlanEntity = getDeliveryPlanEntityById(deliveryPlanId)
        val deliveryEntities = getAllDeliveriesByDeliveryId(deliveryPlanId)
        val deliveryPlan = mapToDeliveryPlan(deliveryPlanEntity, deliveryEntities)
        return deliveryPlan
    }

    private fun getDeliveryPlanEntityById(deliveryPlanId: DeliveryPlanId): DeliveryPlanEntity {
        val deliveryPlanEntity = deliveryPlanJpaRepository.findById(deliveryPlanId.id)
            .orElseThrow { EntityNotFoundException() }

        return deliveryPlanEntity
    }

    private fun getAllDeliveriesByDeliveryId(deliveryPlanId: DeliveryPlanId): List<DeliveryEntity> {
        val deliveryEntities = deliveryJpaRepository.findAllByDeliveryPlanId(deliveryPlanId.id)
        return deliveryEntities
    }

    private fun mapToDeliveryPlan(
        deliveryPlanEntity: DeliveryPlanEntity,
        deliveryEntities: List<DeliveryEntity>
    ): DeliveryPlan {
        val deliveryPlan = deliveryPlanEntityMapper.fromDeliveryPlanEntityToDeliveryPlanDomain(deliveryPlanEntity)
        val deliveries = deliveryEntities.map { deliveryEntityMapper.fromDeliveryEntityToDelivery(it) }
        deliveries.forEach { it.mapDeliveryPlan(deliveryPlan) }

        return deliveryPlan
    }
}