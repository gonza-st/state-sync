package com.rnd.sync.application.domain.deliveryplan

import com.rnd.sync.application.domain.deliveryplan.state.DeliveryPlanCancelledState
import com.rnd.sync.application.domain.deliveryplan.state.DeliveryPlanCreatedState
import com.rnd.sync.application.domain.deliveryplan.state.DeliveryPlanState
import java.time.LocalDate

class DeliveryPlanDecomposite(
    private val deliveryPlanId: DeliveryPlanId? = null,
    val workingDate: LocalDate,
    status: DeliveryPlanState
) {
    val id: DeliveryPlanId
        get() = deliveryPlanId ?: throw IllegalStateException("id should not be null")

    var status = status
        private set

    companion object {
        fun createNew(
            workingDate: LocalDate,
        ): DeliveryPlanDecomposite {
            return DeliveryPlanDecomposite(
                workingDate = workingDate,
                status = DeliveryPlanCreatedState()
            )
        }

        fun create(
            id: Long,
            workingDate: LocalDate,
            status: String
        ): DeliveryPlanDecomposite {
            val allStatus = listOf(DeliveryPlanCreatedState(), DeliveryPlanCancelledState())
            val selectedStatus = allStatus.find { it.name().equals(status, true) }
                ?: throw IllegalArgumentException("DeliveryPlan status $status not found")

            return DeliveryPlanDecomposite(
                deliveryPlanId = DeliveryPlanId(id),
                workingDate = workingDate,
                status = selectedStatus
            )
        }
    }

    data class DeliveryPlanId(val id: Long)
}