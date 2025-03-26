package com.rnd.sync.infra.listener

import com.rnd.sync.application.service.order.`in`.CancelOrderCase
import com.rnd.sync.application.service.order.`in`.CancelOrderCase.CancelOrderRequest
import com.rnd.sync.application.utils.event.DeliveryCancelEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class OrderEventListenerImpl(
    private val cancelOrderCase: CancelOrderCase
) {
    // TODO ("SAGA 고려")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleDeliveryCancelEvent(event: DeliveryCancelEvent) {
        val request = CancelOrderRequest(event.orderId)
        cancelOrderCase.cancelOrder(request)
    }
}