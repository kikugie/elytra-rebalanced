package dev.kikugie.elytra_rebalanced.registry

import dev.kikugie.elytra_rebalanced.Reference
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.ItemGroup
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey

internal inline fun <T : Item> item(constructor: (Item.Settings) -> T, settings: Item.Settings.() -> Unit): T =
    constructor(Item.Settings().apply(settings))

internal inline fun ItemConvertible.group(
    group: RegistryKey<ItemGroup>,
    crossinline action: FabricItemGroupEntries.(ItemConvertible) -> Unit,
) = ItemGroupEvents.modifyEntriesEvent(group).register {
    it.apply { action(this@group) }
}

internal fun <T> Registry<T>.register(id: String, value: T) =
    Registry.register(this, Reference.id(id), value)