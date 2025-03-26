package com.rnd.sync.application.domain.deliveryplan.delivery.state

class DeliveryCompletedState: DeliveryState {
    override fun name(): String {
        return "completed"
    }

    override fun create(): DeliveryState {
        throw IllegalStateException("완료 상태에서는 상태를 바꿀 수 없다")
    }

    override fun start(): DeliveryState {
        throw IllegalStateException("완료 상태에서는 상태를 바꿀 수 없다")
    }

    override fun complete(): DeliveryState {
        throw IllegalStateException("완료 상태에서는 상태를 바꿀 수 없다")
    }

    override fun delay(): DeliveryState {
        throw IllegalStateException("완료 상태에서는 상태를 바꿀 수 없다")
    }

    override fun cancel(): DeliveryState {
        throw IllegalStateException("완료 상태에서는 상태를 바꿀 수 없다")
    }
}