package com.rnd.sync.application.domain.delivery

import com.rnd.sync.application.domain.delivery.state.DeliveryCancelledState
import com.rnd.sync.application.domain.delivery.state.DeliveryCompletedState
import com.rnd.sync.application.domain.delivery.state.DeliveryCreatedState
import com.rnd.sync.application.domain.delivery.state.DeliveryDelayedState
import com.rnd.sync.application.domain.delivery.state.DeliveryStartedState
import com.rnd.sync.application.domain.delivery.state.DeliveryState
import com.rnd.sync.application.domain.deliveryplan.DeliveryPlan.DeliveryPlanId
import com.rnd.sync.application.domain.order.Order.OrderId

class DeliveryDecomposite(
    private val deliveryId: DeliveryId? = null,
    val orderId: OrderId,
    val orderNumber: String,
    val destination: String,
    val driverName: String,
    val deliveryOrder: Int,
    status: DeliveryState
) {
    lateinit var deliveryPlanId: DeliveryPlanId
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

    fun mapDeliveryPlanId(deliveryPlanId: DeliveryPlanId) {
        this.deliveryPlanId = deliveryPlanId
    }

    companion object {
        fun createNew(
            orderId: Long,
            orderNumber: String,
            destination: String,
            driverName: String,
            deliveryOrder: Int,
        ): DeliveryDecomposite {
            return DeliveryDecomposite(
                orderId = OrderId(orderId),
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
        ): DeliveryDecomposite {
            val allStatus = listOf(
                DeliveryCreatedState(),
                DeliveryStartedState(),
                DeliveryDelayedState(),
                DeliveryCompletedState(),
                DeliveryCancelledState(),
            )

            val selectedStatus = allStatus.find { it.name().equals(status, true) }
                ?: throw IllegalArgumentException("Delivery status $status not found")

            return DeliveryDecomposite(
                deliveryId = DeliveryId(id),
                orderId = OrderId(orderId),
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