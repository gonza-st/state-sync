package com.rnd.sync.infra.persistence.deliveryplan

import com.rnd.sync.application.domain.deliveryplan.DeliveryPlan
import com.rnd.sync.application.service.deliveryplan.out.DeliveryPlanRepository
import com.rnd.sync.infra.persistence.deliveryplan.entity.DeliveryPlanEntityMapper
import com.rnd.sync.infra.persistence.deliveryplan.jpa.DeliveryPlanJpaRepository
import org.springframework.stereotype.Repository

@Repository
class DeliveryPlanRepositoryImpl(
    private val deliveryPlanEntityMapper: DeliveryPlanEntityMapper,
    private val deliveryPLanJpaRepository: DeliveryPlanJpaRepository,
) : DeliveryPlanRepository {

    override fun save(deliveryPlan: DeliveryPlan): DeliveryPlan {
        val entity = deliveryPlanEntityMapper
            .fromDeliveryPlanToNewDeliveryPlanEntity(deliveryPlan)

        val savedEntity = deliveryPLanJpaRepository.save(entity)
        val savedDeliveryPlan = deliveryPlanEntityMapper
            .fromDeliveryPlanEntityToDeliveryPlanDomain(savedEntity)

        return savedDeliveryPlan
    }
}