package dev.kikugie.elytra_rebalanced.elytra

import dev.kikugie.elytra_rebalanced.elytra.ElytraController.Status.*
import dev.kikugie.elytra_rebalanced.mixin.access.ElytraControllerAccessor
import dev.kikugie.elytra_rebalanced.util.TickTimer
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.player.PlayerEntity

/**
 * Controls the behaviour of [PaperElytraItem] for each player.
 *
 * Consists of 5 stages:
 * - [INVALID]: Paper elytra isn't equipped;
 * - [UNAVAILABLE]: Paper elytra can't be deployed;
 * - [ALLOWED]: Paper elytra can be deployed, but hasn't been yet;
 * - [ACTIVE]: Paper elytra is deployed, and you fly weee;
 * - [COUNTDOWN]: You have slowed down too much and the paper elytra is about to deactivate.
 */
open class ElytraController(private val player: PlayerEntity) {
    protected val timer = TickTimer(DEACTIVATION_TIME) { if (player.hasPaperElytra) player.stopFallFlying() }
    protected val buffer = TickTimer(BUFFER_TIME)
    val status
        get() = when {
            !player.hasPaperElytra -> INVALID
            player.isOnGround || player.abilities.flying -> UNAVAILABLE
            timer.isActive && timer.current < DEACTIVATION_TIME - 5 -> COUNTDOWN
            timer.isActive && player.isFallFlying -> ACTIVE
            canBeActivated() -> ALLOWED
            else -> UNAVAILABLE
        }

    open fun tick() {
        if (player.isOnGround) {
            timer.cancel()
            buffer.cancel()
            return
        }
        if (buffer.isActive && canBeActivated() && player.hasPaperElytra) {
            player.startFallFlying()
            buffer.cancel()
        } else if (isFlightStable()) timer.reset()
        timer.tick()
        buffer.tick()
    }

    open fun shouldFly(): Boolean = when {
        timer.isActive -> true
        !canBeActivated() -> false
        else -> {
            timer.reset()
            true
        }
    }

    fun resetBuffer(time: Long) {
        if (player.isOnGround || timer.isActive) return
        val delta = (BUFFER_TIME - (player.world.time - time)).coerceAtLeast(1).toInt()
        buffer.reset(delta)
    }

    private fun canBeActivated() = player.fallDistance >= REQUIRED_FALL_DISTANCE
    private fun isFlightStable() = player.velocity.lengthSquared() >= REQUIRED_VELOCITY_SQ

    companion object {
        const val REQUIRED_FALL_DISTANCE = 3
        const val REQUIRED_VELOCITY_SQ = 0.5
        const val DEACTIVATION_TIME = 100
        const val BUFFER_TIME = 20

        @JvmStatic
        val PlayerEntity.controller: ElytraController get() = (this as ElytraControllerAccessor).`elytraRebalanced$getController`()
        @JvmStatic
        val PlayerEntity.hasPaperElytra get() = getEquippedStack(EquipmentSlot.CHEST).item is PaperElytraItem
    }

    enum class Status {
        INVALID, UNAVAILABLE, ALLOWED, ACTIVE, COUNTDOWN
    }
}