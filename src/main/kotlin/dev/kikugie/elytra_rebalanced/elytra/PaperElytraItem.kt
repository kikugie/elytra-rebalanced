package dev.kikugie.elytra_rebalanced.elytra

import dev.kikugie.elytra_rebalanced.Reference
import dev.kikugie.elytra_rebalanced.elytra.ElytraController.Companion.controller
import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ElytraItem
import net.minecraft.item.ItemStack

/**
 * An early game replacement of the standard elytra. Designed to be weaker with the following limitations:
 * - Has 4 times less durability;
 * - Can't be repaired;
 * - Can't be enchanted;
 * - Can't be boosted with rockets;
 * - Requires 3+ blocks of fall distance to deploy;
 * - If slowed down too much while flying, will deactivate.
 *
 * @see [ElytraController]
 */
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