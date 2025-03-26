package com.rnd.sync.infra.persistence.delivery

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "deliveries")
class DeliveryEntity(
    id: Long? = null,
    orderId: Long,
    orderNumber: String,
    destination: String,
    driverName: String,
    deliveryOrder: Int,
    status: String,
    deliveryPlanId: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = id
        protected set

    @Column(nullable = false)
    var orderId: Long = orderId
        protected set

    @Column(nullable = false)
    var orderNumber: String = orderNumber
        protected set

    @Column(nullable = false)
    var destination: String = destination
        protected set

    @Column(nullable = false)
    var driverName: String = driverName
        protected set

    @Column(nullable = false)
    var deliveryOrder: Int = deliveryOrder
        protected set

    @Column(nullable = false)
    var status: String = status
        protected set

    @Column(nullable = false)
    var deliveryPlanId: Long = deliveryPlanId
        protected set

    fun updateId(id: Long) {
        this.id = id
    }
}