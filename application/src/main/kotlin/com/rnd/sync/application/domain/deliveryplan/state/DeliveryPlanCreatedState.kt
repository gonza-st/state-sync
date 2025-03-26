package com.rnd.sync.application.domain.deliveryplan.state

class DeliveryPlanCreatedState : DeliveryPlanState {
    override fun name(): String {
        return "created"
    }

    override fun create(): DeliveryPlanState {
        throw IllegalStateException("생성됨 상태에서 생성할 수 없다")
    }

    override fun cancel(): DeliveryPlanState {
        return DeliveryPlanCancelledState()
    }
}