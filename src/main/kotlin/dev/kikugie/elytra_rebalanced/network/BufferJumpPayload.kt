package dev.kikugie.elytra_rebalanced.network

import dev.kikugie.elytra_rebalanced.Reference
import dev.kikugie.elytra_rebalanced.elytra.PaperElytraItem
import dev.kikugie.elytra_rebalanced.elytra.ElytraController.Status.UNAVAILABLE
import io.netty.buffer.ByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.network.packet.CustomPayload

/**
 * A packet sent by the client to the server if a player wants to deploy [PaperElytraItem] when it's in the [UNAVAILABLE] stage.
 *
 * @property time the current client world time. The server takes the difference between its own time and this to synchronize the action.
 */
class BufferJumpPayload(val time: Long) : CustomPayload {
    override fun getId() = ID

    companion object {
        val ID: CustomPayload.Id<BufferJumpPayload> = CustomPayload.id(Reference.id("buffer_jump").toString())
        val CODEC: PacketCodec<ByteBuf, BufferJumpPayload> = PacketCodec.tuple(PacketCodecs.VAR_LONG, BufferJumpPayload::time, ::BufferJumpPayload)
    }
}