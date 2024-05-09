package dev.kikugie.elytra_rebalanced.mixin.common;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.kikugie.elytra_rebalanced.elytra.ElytraController;
import dev.kikugie.elytra_rebalanced.elytra.PaperElytraItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FireworkRocketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Disables firework rockets for {@link PaperElytraItem}
 */
@Mixin(FireworkRocketItem.class)
public class FireworkRocketItemMixin {
    @ModifyExpressionValue(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isFallFlying()Z"))
    private boolean cancelFireworkLaunch(boolean original, @Local(argsOnly = true) PlayerEntity playerEntity) {
        return original && !ElytraController.getHasPaperElytra(playerEntity);
    }
}