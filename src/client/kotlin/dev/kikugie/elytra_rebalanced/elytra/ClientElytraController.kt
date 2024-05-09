package dev.kikugie.elytra_rebalanced.elytra

import dev.kikugie.elytra_rebalanced.network.BufferJumpPayload
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.client.network.ClientPlayerEntity

class ClientElytraController(private val clientPlayer: ClientPlayerEntity) : ElytraController(clientPlayer) {
    private var hasJumped = false

    override fun tick() {
        if (!clientPlayer.input.jumping) hasJumped = false
        super.tick()
    }

    override fun shouldFly(): Boolean = super.shouldFly().also { if (!it)
        if (!hasJumped && clientPlayer.input.jumping) {
            ClientPlayNetworking.send(BufferJumpPayload(clientPlayer.world.time))
            buffer.reset()
            hasJumped = true
        }
    }
}