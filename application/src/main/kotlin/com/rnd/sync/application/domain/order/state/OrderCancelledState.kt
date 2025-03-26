package com.rnd.sync.application.domain.order.state

import java.lang.IllegalStateException

class OrderCancelledState : OrderState {
    override fun name(): String {
        return "cancelled"
    }

    override fun create(): OrderState {
        throw IllegalStateException("취소 상태에서 다시 생성될 수 없습니다.")
    }

    override fun cancel(): OrderState {
        throw IllegalStateException("이미 취소되었습니다.")
    }
}