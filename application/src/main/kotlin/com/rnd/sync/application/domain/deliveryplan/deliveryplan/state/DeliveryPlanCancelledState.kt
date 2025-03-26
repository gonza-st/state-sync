package com.rnd.sync.application.domain.deliveryplan.deliveryplan.state

class DeliveryPlanCancelledState : DeliveryPlanState {
    override fun name(): String {
        return "cancelled"
    }

    override fun create(): DeliveryPlanState {
        throw IllegalStateException("취소됨 상태에서 상태를 변경할 수 없다")
    }

    override fun cancel(): DeliveryPlanState {
        throw IllegalStateException("취소됨 상태에서 상태를 변경할 수 없다")
    }
}