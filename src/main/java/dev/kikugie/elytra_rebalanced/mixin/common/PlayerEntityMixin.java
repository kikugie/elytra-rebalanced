package dev.kikugie.elytra_rebalanced.mixin.common;

import dev.kikugie.elytra_rebalanced.mixin.access.ElytraControllerAccessor;
import dev.kikugie.elytra_rebalanced.elytra.ElytraController;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Manages serverside and common {@link ElytraController}.
 */
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements ElytraControllerAccessor {
    @Shadow public abstract void sendMessage(Text message, boolean overlay);

    @Shadow public abstract void startFallFlying();

    @Unique
    public final ElytraController controller = elytraRebalanced$createController();

    @Inject(method = "tickMovement", at = @At("TAIL"))
    private void tickController(CallbackInfo ci) {
        controller.tick();
        sendMessage(Text.of(controller.getStatus().name()), true);
    }

    @Override
    public ElytraController elytraRebalanced$getController() {
        return controller;
    }

    @Override
    public ElytraController elytraRebalanced$createController() {
        return new ElytraController((PlayerEntity) (Object) this);
    }
}