package dev.kikugie.elytra_rebalanced

import dev.kikugie.elytra_rebalanced.network.ModNetworking
import dev.kikugie.elytra_rebalanced.registry.ModItems
import net.fabricmc.api.ModInitializer

object CommonInit : ModInitializer {
    override fun onInitialize() {
        ModItems.registerModItems()
        ModNetworking.registerPackets()
    }
}