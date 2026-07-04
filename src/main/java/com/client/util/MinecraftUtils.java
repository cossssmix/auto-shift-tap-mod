package com.client.util;

import static com.client.util.MinecraftVariables.mc;

import org.jspecify.annotations.NonNull;

import lombok.Getter;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public final class MinecraftUtils {
	@Getter
	private static final MinecraftUtils instance = new MinecraftUtils();

	private MinecraftUtils() {}

	public boolean canDealFullHit(final @NonNull PlayerEntity player) {
		final float tickProgress = mc.getRenderTickCounter().getTickProgress(true);

		return player.getAttackCooldownProgress(tickProgress) >= 0.9f;
	}

	public boolean canCrit(final @NonNull PlayerEntity player) {
		return !player.getAbilities().flying
			&& player.getPose() != EntityPose.GLIDING
			&& !player.isSubmergedInWater()
			&& this.canDealFullHit(player)
			&& !player.isOnGround()
			&& this.isLife(player)
			&& player.fallDistance > 0.0f;
	}

	public boolean isLife(final @NonNull LivingEntity livingEntity) {
		return livingEntity.isAlive()
			&& !livingEntity.isDead()
			&& livingEntity.deathTime == 0;
	}

	public void attack(final @NonNull LivingEntity target) {
		mc.interactionManager.attackEntity(mc.player, target);
		mc.player.swingHand(Hand.MAIN_HAND);
	}
}
