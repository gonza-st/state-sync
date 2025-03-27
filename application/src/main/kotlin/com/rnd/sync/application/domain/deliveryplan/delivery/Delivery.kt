package com.rnd.sync.application.domain.deliveryplan.delivery

import com.rnd.sync.application.domain.deliveryplan.delivery.state.DeliveryCancelledState
import com.rnd.sync.application.domain.deliveryplan.delivery.state.DeliveryCompletedState
import com.rnd.sync.application.domain.deliveryplan.delivery.state.DeliveryCreatedState
import com.rnd.sync.application.domain.deliveryplan.delivery.state.DeliveryDelayedState
import com.rnd.sync.application.domain.deliveryplan.delivery.state.DeliveryStartedState
import com.rnd.sync.application.domain.deliveryplan.delivery.state.DeliveryState
import com.rnd.sync.application.domain.deliveryplan.deliveryplan.DeliveryPlan

class Delivery(
    private val deliveryId: DeliveryId? = null,
    val orderId: Long,
    val orderNumber: String,
    val destination: String,
    val driverName: String,
    val deliveryOrder: Int,
    status: DeliveryState
) {
    lateinit var deliveryPlan: DeliveryPlan
        private set

    val id: DeliveryId
        get() = deliveryId ?: throw IllegalStateException("id should not be null")

    var status: DeliveryState = status
        private set

    fun start() {
        status = status.start()
    }

    fun delay() {
        status = status.delay()
    }

    fun cancel() {
        status = status.cancel()
        deliveryPlan.syncStatus()
    }

    fun complete() {
        status = status.complete()
    }

    fun create() {
        status = status.create()
    }

    fun mapDeliveryPlan(deliveryPlan: DeliveryPlan) {
        deliveryPlan.mapDelivery(this)
        this.deliveryPlan = deliveryPlan
    }

    companion object {
        fun createNew(
            orderId: Long,
            orderNumber: String,
            destination: String,
            driverName: String,
            deliveryOrder: Int,
        ): Delivery {
            return Delivery(
                orderId = orderId,
                orderNumber = orderNumber,
                destination = destination,
                driverName = driverName,
                deliveryOrder = deliveryOrder,
                status = DeliveryCreatedState()
            )
        }

        fun create(
            id: Long,
            orderId: Long,
            orderNumber: String,
            destination: String,
            driverName: String,
            deliveryOrder: Int,
            status: String
        ): Delivery {
            val allStatus = listOf(
                DeliveryCreatedState(),
                DeliveryStartedState(),
                DeliveryDelayedState(),
                DeliveryCompletedState(),
                DeliveryCancelledState(),
            )

            val selectedStatus = allStatus.find { it.name().equals(status, true) }
                ?: throw IllegalArgumentException("Delivery status $status not found")

            return Delivery(
                deliveryId = DeliveryId(id),
                orderId = orderId,
                orderNumber = orderNumber,
                destination = destination,
                driverName = driverName,
                deliveryOrder = deliveryOrder,
                status = selectedStatus
            )
        }
    }

    data class DeliveryId(val id: Long)
}