package dev.kikugie.elytra_rebalanced.network

import dev.kikugie.elytra_rebalanced.elytra.ElytraController.Companion.controller
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.network.packet.CustomPayload

object ModNetworking {
    fun registerPackets() {
        playerReceiver(BufferJumpPayload.ID) { payload, context ->
            context.player().controller.resetBuffer(payload.time)
        }
    }

    private inline fun <T : CustomPayload> playerReceiver(type: CustomPayload.Id<T>, crossinline action: (T, ServerPlayNetworking.Context) -> Unit) {
        ServerPlayConnectionEvents.INIT.register { handler, _ ->
            ServerPlayNetworking.registerReceiver(handler, type) { payload, context ->
                action(payload, context)
            }
        }
        ServerPlayConnectionEvents.DISCONNECT.register { handler, _ ->
            ServerPlayNetworking.unregisterReceiver(handler, type.id)
        }
    }
}