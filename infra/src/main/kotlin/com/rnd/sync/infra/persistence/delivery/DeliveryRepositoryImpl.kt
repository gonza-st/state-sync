package com.rnd.sync.infra.persistence.delivery

import com.rnd.sync.application.domain.delivery.Delivery
import com.rnd.sync.application.service.delivery.out.DeliveryRepository
import com.rnd.sync.infra.persistence.delivery.entity.DeliveryEntityMapper
import com.rnd.sync.infra.persistence.delivery.jpa.DeliveryJpaRepository
import org.springframework.stereotype.Repository

@Repository
class DeliveryRepositoryImpl(
    private val deliveryEntityMapper: DeliveryEntityMapper,
    private val deliveryJpaRepository: DeliveryJpaRepository
): DeliveryRepository {
    override fun save(delivery: Delivery): Delivery {
        val entity = deliveryEntityMapper.fromDeliveryToNewDeliveryEntity(delivery)
        val savedEntity = deliveryJpaRepository.save(entity)

        val savedDelivery = deliveryEntityMapper.fromDeliveryEntityToDeliveryDomain(savedEntity)
        return savedDelivery
    }
}