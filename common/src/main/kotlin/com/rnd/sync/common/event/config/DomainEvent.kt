package com.rnd.sync.common.event.config

abstract class DomainEvent<T>(
    val eventType: EventType,
    val payload: T
)