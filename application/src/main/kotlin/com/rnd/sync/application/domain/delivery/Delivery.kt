package com.rnd.sync.application.domain.delivery

import com.rnd.sync.application.domain.delivery.state.DeliveryCreatedState
import com.rnd.sync.application.domain.delivery.state.DeliveryState
import com.rnd.sync.application.domain.deliveryplan.DeliveryPlan

class Delivery(
    private val deliveryId: DeliveryId? = null,
    val orderNumber: String,
    val destination: String,
    val driverName: String,
    val deliveryOrder: Int,
    status: DeliveryState = DeliveryCreatedState()
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
    }

    fun complete() {
        status = status.complete()
    }

    fun create() {
        status = status.create()
    }

    fun mapDeliveryPlan(deliveryPlan: DeliveryPlan) {
        this.deliveryPlan = deliveryPlan
    }

    data class DeliveryId(val id: Long)
}