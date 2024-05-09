package dev.kikugie.elytra_rebalanced

import dev.kikugie.elytra_rebalanced.network.BufferJumpPayload
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.minecraft.client.MinecraftClient

object ClientInit : ClientModInitializer {
    val CLIENT: MinecraftClient = MinecraftClient.getInstance()
    override fun onInitializeClient() {
        registerPayloads()
    }

    private fun registerPayloads() {
        PayloadTypeRegistry.playC2S().register(BufferJumpPayload.ID, BufferJumpPayload.CODEC)
    }
}