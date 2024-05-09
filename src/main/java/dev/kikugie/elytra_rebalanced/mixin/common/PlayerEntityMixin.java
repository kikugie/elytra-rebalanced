package dev.kikugie.elytra_rebalanced.mixin.common;

import dev.kikugie.elytra_rebalanced.mixin.access.ElytraControllerAccessor;
import dev.kikugie.elytra_rebalanced.elytra.ElytraController;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements ElytraControllerAccessor {
    @Unique
    public final ElytraController controller = elytraRebalanced$createController();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tickMovement", at = @At("TAIL"))
    private void tickController(CallbackInfo ci) {
        controller.tick();
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