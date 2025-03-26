package com.rnd.sync.application.domain.delivery.state

interface DeliveryState {
    fun create(): DeliveryState

    fun start(): DeliveryState

    fun complete(): DeliveryState

    fun delay(): DeliveryState

    fun cancel(): DeliveryState
}