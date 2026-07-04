package com.client.mixin.client;

import com.client.Client;
import com.client.systems.module.combat.ShiftTapModule;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {
    @Inject(method = "attackEntity", at = @At("HEAD"))
    private void onAttackEntity(PlayerEntity player, Entity target, CallbackInfo ci) {
        if (target instanceof LivingEntity) {
            final ShiftTapModule shiftTapModule = Client.getContext().getModuleRepository().getModule(ShiftTapModule.class);
            if (shiftTapModule != null) {
                shiftTapModule.onAttack();
            }
        }
    }
}
