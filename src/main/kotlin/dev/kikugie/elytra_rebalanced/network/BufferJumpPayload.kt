package dev.kikugie.elytra_rebalanced.network

import dev.kikugie.elytra_rebalanced.Reference
import io.netty.buffer.ByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.network.packet.CustomPayload

class BufferJumpPayload(val time: Long) : CustomPayload {
    override fun getId() = ID

    companion object {
        val ID: CustomPayload.Id<BufferJumpPayload> = CustomPayload.id(Reference.id("buffer_jump").toString())
        val CODEC: PacketCodec<ByteBuf, BufferJumpPayload> = PacketCodec.tuple(PacketCodecs.VAR_LONG, BufferJumpPayload::time, ::BufferJumpPayload)
    }
}