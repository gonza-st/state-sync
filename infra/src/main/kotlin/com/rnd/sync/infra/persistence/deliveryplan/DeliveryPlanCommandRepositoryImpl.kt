package com.rnd.sync.infra.persistence.deliveryplan

import com.rnd.sync.application.domain.delivery.Delivery
import com.rnd.sync.application.domain.deliveryplan.DeliveryPlan
import com.rnd.sync.application.service.deliveryplan.out.DeliveryPlanCommandRepository
import com.rnd.sync.infra.persistence.delivery.entity.DeliveryEntity
import com.rnd.sync.infra.persistence.delivery.entity.DeliveryEntityMapper
import com.rnd.sync.infra.persistence.delivery.jpa.DeliveryJpaRepository
import com.rnd.sync.infra.persistence.deliveryplan.entity.DeliveryPlanEntity
import com.rnd.sync.infra.persistence.deliveryplan.entity.DeliveryPlanEntityMapper
import com.rnd.sync.infra.persistence.deliveryplan.jpa.DeliveryPlanJpaRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class DeliveryPlanCommandRepositoryImpl(
    private val deliveryEntityMapper: DeliveryEntityMapper,
    private val deliveryJpaRepository: DeliveryJpaRepository,
    private val deliveryPlanEntityMapper: DeliveryPlanEntityMapper,
    private val deliveryPlanJpaRepository: DeliveryPlanJpaRepository,
): DeliveryPlanCommandRepository {


    override fun save(deliveryPlan: DeliveryPlan): DeliveryPlan {
        val savedEntity = saveDeliveryPlan(deliveryPlan = deliveryPlan)
        val savedEntityId = savedEntity.id ?: throw EntityNotFoundException()

        val savedDeliveryEntities = saveAllDeliveries(
            deliveries = deliveryPlan.deliveries,
            deliveryPlanId = savedEntityId
        )

        val savedDeliveryPlan = mapToDeliveryPlan(savedEntity, savedDeliveryEntities)
        return savedDeliveryPlan
    }


    override fun update(deliveryPlan: DeliveryPlan): DeliveryPlan {
        val updatedEntity = updateDeliveryPlan(deliveryPlan = deliveryPlan)
        val updatedEntityId = updatedEntity.id ?: throw EntityNotFoundException()

        val updatedDeliveries = updateAllDeliveries(
            deliveries = deliveryPlan.deliveries,
            deliveryPlanId = updatedEntityId
        )

        val updatedDeliveryPlan = mapToDeliveryPlan(updatedEntity, updatedDeliveries)
        return updatedDeliveryPlan
    }

    private fun saveDeliveryPlan(deliveryPlan: DeliveryPlan): DeliveryPlanEntity {
        val entity = deliveryPlanEntityMapper
            .fromDeliveryPlanToNewDeliveryPlanEntity(deliveryPlan)

        val savedEntity = deliveryPlanJpaRepository.save(entity)
        return savedEntity
    }

    private fun saveAllDeliveries(deliveries: List<Delivery>, deliveryPlanId: Long): List<DeliveryEntity> {
        val entities = deliveries.map { deliveryEntityMapper.fromDeliveryToNewDeliveryEntity(it, deliveryPlanId) }
        val savedEntities = deliveryJpaRepository.saveAll(entities)
        return savedEntities
    }

    private fun updateDeliveryPlan(deliveryPlan: DeliveryPlan): DeliveryPlanEntity {
        val entity = deliveryPlanEntityMapper.fromDeliveryPlanToDeliveryPlanEntity(deliveryPlan)
        val updatedEntity = deliveryPlanJpaRepository.save(entity)
        return updatedEntity
    }

    private fun updateAllDeliveries(deliveries: List<Delivery>, deliveryPlanId: Long): List<DeliveryEntity> {
        val entities = deliveries.map { deliveryEntityMapper.fromDeliveryToDeliveryEntity(it, deliveryPlanId) }
        val updatedEntities = deliveryJpaRepository.saveAll(entities)
        return updatedEntities
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