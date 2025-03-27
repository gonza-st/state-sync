package com.rnd.sync.application.domain.deliveryplan.deliveryplan

import com.rnd.sync.application.domain.deliveryplan.delivery.Delivery
import com.rnd.sync.application.domain.deliveryplan.delivery.Delivery.DeliveryId
import com.rnd.sync.application.domain.deliveryplan.delivery.state.DeliveryCancelledState
import com.rnd.sync.application.domain.deliveryplan.deliveryplan.state.DeliveryPlanCancelledState
import com.rnd.sync.application.domain.deliveryplan.deliveryplan.state.DeliveryPlanCreatedState
import com.rnd.sync.application.domain.deliveryplan.deliveryplan.state.DeliveryPlanState
import com.rnd.sync.application.domain.order.Order
import java.time.LocalDate

class DeliveryPlan(
    private val deliveryPlanId: DeliveryPlanId? = null,
    val workingDate: LocalDate,
    status: DeliveryPlanState
) {
    val id: DeliveryPlanId
        get() = deliveryPlanId ?: throw IllegalStateException("id should not be null")

    var status = status
        private set

    // TODO ("일급 컬렉션 만들기")
    private val mutableDeliveries = mutableListOf<Delivery>()
    val deliveries: List<Delivery>
        get() = mutableDeliveries.toList()

    fun mapDelivery(delivery: Delivery) {
        this.mutableDeliveries.add(delivery)
    }

    fun cancel() {
        deliveries.forEach { it.cancel() }
    }

    fun syncStatus() {
        if (isAllCancelled()) {
            status = status.cancel()
        }
    }

    private fun isAllCancelled(): Boolean {
        val result = deliveries
            .filter { it.status is DeliveryCancelledState }

        return deliveries.size == result.size
    }

    fun startDelivery(id: DeliveryId) {
        val delivery = findDelivery(id)
        delivery.start()
    }

    fun cancelDelivery(id: DeliveryId) {
        val delivery = findDelivery(id)
        delivery.cancel()
    }

    fun cancelDeliveryByOrderId(orderId: Long) {
        val delivery = findDeliveryByOrderId(orderId)
        delivery.cancel()
    }

    fun delayDelivery(id: DeliveryId) {
        val delivery = findDelivery(id)
        delivery.delay()
    }

    fun completeDelivery(id: DeliveryId) {
        val delivery = findDelivery(id)
        delivery.complete()
    }

    // fixme
    fun getDelivery(id: DeliveryId): Delivery {
        return findDelivery(id)
    }

    private fun findDelivery(id: DeliveryId): Delivery {
        val result = deliveries.find { it.id == id }
        return result ?: throw IllegalArgumentException("해당 id 없음")
    }

    private fun findDeliveryByOrderId(orderId: Long): Delivery {
        val result = deliveries.find { it.orderId == orderId }
        return result ?: throw IllegalArgumentException("해당 order id 없음")
    }

    companion object {
        fun createNew(
            workingDate: LocalDate,
        ): DeliveryPlan {
            return DeliveryPlan(
                workingDate = workingDate,
                status = DeliveryPlanCreatedState()
            )
        }

        fun create(
            id: Long,
            workingDate: LocalDate,
            status: String
        ): DeliveryPlan {
            val allStatus = listOf(DeliveryPlanCreatedState(), DeliveryPlanCancelledState())
            val selectedStatus = allStatus.find { it.name().equals(status, true) }
                ?: throw IllegalArgumentException("DeliveryPlan status $status not found")

            return DeliveryPlan(
                deliveryPlanId = DeliveryPlanId(id),
                workingDate = workingDate,
                status = selectedStatus
            )
        }
    }

    data class DeliveryPlanId(val id: Long)
}