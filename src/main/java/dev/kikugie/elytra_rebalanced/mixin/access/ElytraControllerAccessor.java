package dev.kikugie.elytra_rebalanced.mixin.access;

import dev.kikugie.elytra_rebalanced.elytra.ElytraController;

public interface ElytraControllerAccessor {
    ElytraController elytraRebalanced$getController();

    ElytraController elytraRebalanced$createController();
}
