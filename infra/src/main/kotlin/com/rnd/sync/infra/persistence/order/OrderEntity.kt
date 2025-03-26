package com.rnd.sync.infra.persistence.order

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "orders")
class OrderEntity(
    id: Long? = null,
    productName: String,
    orderNumber: String,
    receiverName: String,
    receiverContact: String,
    receiverAddress: String,
    status: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = id
        protected set

    @Column(nullable = false)
    var productName: String = productName
        protected set

    @Column(nullable = false)
    var orderNumber: String = orderNumber
        protected set

    @Column(nullable = false)
    var receiverName: String = receiverName
        protected set

    @Column(nullable = false)
    var receiverContact: String = receiverContact
        protected set

    @Column(nullable = false)
    var receiverAddress: String = receiverAddress
        protected set

    @Column(nullable = false)
    var status: String = status
        protected set

    fun updateId(id: Long) {
        this.id = id
    }
}