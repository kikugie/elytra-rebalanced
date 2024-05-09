package dev.kikugie.elytra_rebalanced.mixin.client;

import dev.kikugie.elytra_rebalanced.mixin.access.ElytraControllerAccessor;
import dev.kikugie.elytra_rebalanced.elytra.ClientElytraController;
import dev.kikugie.elytra_rebalanced.elytra.ElytraController;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin implements ElytraControllerAccessor {
    @Override
    public ElytraController elytraRebalanced$createController() {
        return new ClientElytraController((ClientPlayerEntity) (Object) this);
    }
}