package com.rnd.sync.application.domain.delivery.state

class DeliveryStartedState : DeliveryState {
    override fun create(): DeliveryState {
        throw IllegalStateException("시작 상태에서 생성됨이 될 수 없다.")
    }

    override fun start(): DeliveryState {
        TODO("Not yet implemented")
    }

    override fun complete(): DeliveryState {
        return DeliveryCompletedState()
    }

    override fun delay(): DeliveryState {
        return DeliveryDelayedState()
    }

    override fun cancel(): DeliveryState {
        throw IllegalStateException("시작 상태에서 취소될 수 없다.")
    }
}