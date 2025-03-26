package com.rnd.sync.infra.persistence.delivery

import com.rnd.sync.application.domain.delivery.DeliveryDecomposite
import com.rnd.sync.application.domain.delivery.Delivery
import com.rnd.sync.application.domain.delivery.Delivery.DeliveryId
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
    override fun get(deliveryId: DeliveryId): DeliveryDecomposite {
        val foundEntity = deliveryJpaRepository.findById(deliveryId.id)
            .orElseThrow { EntityNotFoundException("Delivery with id $deliveryId not found") }

        val foundDelivery = deliveryEntityMapper.fromDeliveryEntityToDeliveryDecomposite(foundEntity)
        return foundDelivery
    }
}