package com.rnd.sync.infra.persistence.delivery

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "delivery_plans")
class DeliveryPlanEntity(
    id: Long? = null,
    workingDate: LocalDate,
    status: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = id
        protected set

    @Column(nullable = false)
    var workingDate: LocalDate = workingDate
        protected set

    @Column(nullable = false)
    var status: String = status
        protected set

    fun updateId(id: Long) {
        this.id = id
    }

    override fun toString(): String {
        return "DeliveryPlanEntity(id=$id, " +
                "workingDate=$workingDate" +
                ")"
    }
}