package com.client.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

@Mixin(Entity.class)
public interface EntityAccessor {
	@Invoker("movementInputToVelocity")
	public static Vec3d movementInputToVelocity(Vec3d movementInput, float speed, float yaw) {
		throw new AssertionError();
	}
}