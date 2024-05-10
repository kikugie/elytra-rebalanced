package dev.kikugie.elytra_rebalanced.registry

import dev.kikugie.elytra_rebalanced.elytra.PaperElytraItem
import net.minecraft.item.ItemGroups
import net.minecraft.item.Items
import net.minecraft.registry.Registries

@Suppress("MemberVisibilityCanBePrivate")
object ModItems {
    val PAPER_ELYTRA = item(::PaperElytraItem) {
        maxDamage(196)
        maxCount(1)
    }

    fun registerModItems(): Unit = with(Registries.ITEM) {
        register("paper_elytra", PAPER_ELYTRA).group(ItemGroups.TOOLS) {
            addAfter(Items.ELYTRA, it)
        }
    }
}