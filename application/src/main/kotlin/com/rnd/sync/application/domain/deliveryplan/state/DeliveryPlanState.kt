package com.rnd.sync.application.domain.deliveryplan.state

interface DeliveryPlanState {
    fun name(): String

    fun create(): DeliveryPlanState

    fun cancel(): DeliveryPlanState
}