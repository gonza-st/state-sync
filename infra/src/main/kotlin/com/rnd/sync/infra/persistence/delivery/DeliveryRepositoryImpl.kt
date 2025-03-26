package com.rnd.sync.infra.persistence.delivery

import com.rnd.sync.application.domain.delivery.Delivery
import com.rnd.sync.application.domain.delivery.DeliveryComposite
import com.rnd.sync.application.domain.delivery.DeliveryComposite.DeliveryId
import com.rnd.sync.application.service.delivery.out.DeliveryRepository
import com.rnd.sync.infra.persistence.delivery.entity.DeliveryEntityMapper
import com.rnd.sync.infra.persistence.delivery.jpa.DeliveryJpaRepository
import jakarta.persistence.EntityNotFoundException
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

    override fun save(deliveryComposite: DeliveryComposite): DeliveryId {
        val entity = deliveryEntityMapper.fromDeliveryCompositeToNewDeliveryEntity(deliveryComposite)
        val savedEntity = deliveryJpaRepository.save(entity)
        val entityId = savedEntity.id ?: throw EntityNotFoundException()

        val deliveryId = DeliveryId(entityId)
        return deliveryId
    }

    override fun get(deliveryId: DeliveryId): Delivery {
        val foundEntity = deliveryJpaRepository.findById(deliveryId.id)
            .orElseThrow { EntityNotFoundException("Delivery with id $deliveryId not found") }

        val foundDelivery = deliveryEntityMapper.fromDeliveryEntityToDeliveryDomain(foundEntity)
        return foundDelivery
    }
}