package dev.kikugie.elytra_rebalanced.util

class TickTimer(private val init: Int, val onExpire: () -> Unit = {}) {
    var current: Int = -1
        private set
    val isActive get() = current > 0

    fun tick() {
        if (current >= 0) current--
        if (current == 0) onExpire()
    }

    fun cancel() {
        current = -1
    }

    fun reset(value: Int = init) {
        current = value
    }
}