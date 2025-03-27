package com.rnd.sync.infra.listener

import com.rnd.sync.application.service.deliveryplan.`in`.CancelDeliveryCase
import com.rnd.sync.application.service.deliveryplan.`in`.CancelDeliveryCase.CancelDeliveryCaseRequest
import com.rnd.sync.common.event.delivery.OrderCancelledEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class DeliveryEventListener(
    private val cancelDeliveryCase: CancelDeliveryCase
) {
    // TODO ("롤백 방법 고민필요")
    // TODO ("주문 취소가 다시 배송 취소를 일으키려면 어떻게 순환참조를 피할수 있는가")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleOrderCancelledEvent(event: OrderCancelledEvent) {
        val request = CancelDeliveryCaseRequest(orderId = event.payload.orderId)
        cancelDeliveryCase.cancelDelivery(request)
    }
}