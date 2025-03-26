package com.rnd.sync.application.domain.order.state

import java.lang.IllegalStateException

class OrderCreatedState : OrderState {
    override fun name(): String {
        return "created"
    }

    override fun create(): OrderState {
        throw IllegalStateException("이미 생성됨 상태입니다")
    }

    override fun cancel(): OrderState {
        return OrderCancelledState()
    }
}