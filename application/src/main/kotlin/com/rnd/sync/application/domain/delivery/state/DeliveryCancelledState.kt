package com.rnd.sync.application.domain.delivery.state

class DeliveryCancelledState:  DeliveryState {
    override fun create():  DeliveryState {
        throw IllegalStateException("취소 상태에서는 상태를 바꿀 수 없다")
    }

    override fun start():  DeliveryState {
        throw IllegalStateException("취소 상태에서는 상태를 바꿀 수 없다")
    }

    override fun complete():  DeliveryState {
        throw IllegalStateException("취소 상태에서는 상태를 바꿀 수 없다")
    }

    override fun delay():  DeliveryState {
        throw IllegalStateException("취소 상태에서는 상태를 바꿀 수 없다")
    }

    override fun cancel():  DeliveryState {
        throw IllegalStateException("취소 상태에서는 상태를 바꿀 수 없다")
    }
}