package com.rnd.sync.application.domain.delivery.state

class DeliveryCreatedState:  DeliveryState {
    override fun name(): String {
        return "created"
    }

    override fun create():  DeliveryState {
        throw IllegalStateException("생성됨 상태에서 생성됨이 될 수 없습니다.")
    }

    override fun start():  DeliveryState {
        return  DeliveryStartedState()
    }

    override fun complete():  DeliveryState {
        throw IllegalStateException("생성됨 상태에서 완료됨이 될 수 없습니다.")
    }

    override fun delay():  DeliveryState {
        throw IllegalStateException("생성됨 상태에서 지연됨이 될 수 없습니다.")
    }

    override fun cancel():  DeliveryState {
        return  DeliveryCancelledState()
    }
}