package com.rnd.sync.application.domain.order.state

interface OrderState {
    fun name(): String

    fun create(): OrderState

    fun cancel(): OrderState
}