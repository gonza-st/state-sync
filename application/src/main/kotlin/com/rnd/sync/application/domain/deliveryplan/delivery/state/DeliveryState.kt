package com.rnd.sync.application.domain.deliveryplan.delivery.state

interface DeliveryState {
    fun name(): String

    fun create(): DeliveryState

    fun start(): DeliveryState

    fun complete(): DeliveryState

    fun delay(): DeliveryState

    fun cancel(): DeliveryState
}