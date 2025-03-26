package com.rnd.sync.infra.persistence.deliveryplan

import com.rnd.sync.application.domain.delivery.Delivery
import com.rnd.sync.application.domain.deliveryplan.DeliveryPlan
import com.rnd.sync.application.domain.deliveryplan.DeliveryPlan.DeliveryPlanId
import com.rnd.sync.application.service.deliveryplan.out.DeliveryPlanRepository
import com.rnd.sync.infra.persistence.delivery.entity.DeliveryEntity
import com.rnd.sync.infra.persistence.delivery.entity.DeliveryEntityMapper
import com.rnd.sync.infra.persistence.delivery.jpa.DeliveryJpaRepository
import com.rnd.sync.infra.persistence.deliveryplan.entity.DeliveryPlanEntity
import com.rnd.sync.infra.persistence.deliveryplan.entity.DeliveryPlanEntityMapper
import com.rnd.sync.infra.persistence.deliveryplan.jpa.DeliveryPlanJpaRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Repository

@Repository
class DeliveryPlanRepositoryImpl(
    private val deliveryEntityMapper: DeliveryEntityMapper,
    private val deliveryJpaRepository: DeliveryJpaRepository,
    private val deliveryPlanEntityMapper: DeliveryPlanEntityMapper,
    private val deliveryPlanJpaRepository: DeliveryPlanJpaRepository,
    private val deliveryJpaRepository: DeliveryJpaRepository,
) : DeliveryPlanRepository {

    override fun save(deliveryPlan: DeliveryPlan): DeliveryPlan {
        val savedEntity = saveDeliveryPlan(deliveryPlan = deliveryPlan)
        val savedEntityId = savedEntity.id ?: throw EntityNotFoundException()

        val savedDeliveryEntities = saveAllDelivery(
            deliveries = deliveryPlan.deliveries,
            deliveryPlanId = savedEntityId
        )

        val savedDeliveryPlan = deliveryPlanEntityMapper
            .fromDeliveryPlanEntityToDeliveryPlanDomain(savedEntity)

        val savedDeliveries = savedDeliveryEntities.map {
            deliveryEntityMapper.fromDeliveryEntityToDelivery(it)
        }

        savedDeliveries.forEach { it.mapDeliveryPlan(savedDeliveryPlan) }
        return savedDeliveryPlan
    }

    private fun saveDeliveryPlan(deliveryPlan: DeliveryPlan): DeliveryPlanEntity {
        val entity = deliveryPlanEntityMapper
            .fromDeliveryPlanToNewDeliveryPlanEntity(deliveryPlan)

        val savedEntity = deliveryPLanJpaRepository.save(entity)
        return savedEntity
    }

    private fun saveAllDelivery(deliveries: List<Delivery>, deliveryPlanId: Long): List<DeliveryEntity> {
        val entities = deliveries.map { deliveryEntityMapper.fromDeliveryToNewDeliveryEntity(it, deliveryPlanId) }
        val savedEntities = deliveryJpaRepository.saveAll(entities)
        return savedEntities
    }

    override fun get(deliveryPlanId: DeliveryPlanId): DeliveryPlan {
        val deliveryPlanEntity = deliveryPlanJpaRepository.findById(deliveryPlanId.id)
            .orElseThrow { EntityNotFoundException() }
        val deliveryPlan = deliveryPlanEntityMapper.fromDeliveryPlanEntityToDeliveryPlanDomain(deliveryPlanEntity)

        val deliveryEntities = deliveryJpaRepository.findAllByDeliveryPlanId(deliveryPlan.id.id)
        val deliveryComposites = deliveryEntities.map { deliveryEntityMapper.fromDeliveryEntityToDeliveryComposite(it) }

        deliveryComposites.forEach { it.mapDeliveryPlan(deliveryPlan) }
        return deliveryPlan
    }
}