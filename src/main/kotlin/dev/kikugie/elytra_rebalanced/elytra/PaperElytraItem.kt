package dev.kikugie.elytra_rebalanced.elytra

import dev.kikugie.elytra_rebalanced.Reference
import dev.kikugie.elytra_rebalanced.elytra.ElytraController.Companion.controller
import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ElytraItem
import net.minecraft.item.ItemStack

class PaperElytraItem(settings: Settings) : ElytraItem(settings), FabricElytraItem {
    override fun canRepair(stack: ItemStack, ingredient: ItemStack) = false

    override fun useCustomElytra(entity: LivingEntity, chestStack: ItemStack, tickElytra: Boolean): Boolean = when {
        chestStack.item !is PaperElytraItem -> false
        entity !is PlayerEntity -> false
        !entity.controller.shouldFly() -> false
        tickElytra -> {
            doVanillaElytraTick(entity, chestStack)
            true
        }
        else -> true
    }

    companion object {
        @JvmField
        val TEXTURE = Reference.id("textures/models/paper_elytra.png")
    }
}